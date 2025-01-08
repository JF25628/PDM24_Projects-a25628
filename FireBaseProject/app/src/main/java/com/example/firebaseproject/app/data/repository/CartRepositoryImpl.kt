package com.example.firebaseproject.app.data.repository


import android.util.Log
import com.example.firebaseproject.app.data.remote.model.CartDTO
import com.example.firebaseproject.app.data.remote.model.CartItemDTO
import com.example.firebaseproject.app.domain.model.CartItemModel
import com.example.firebaseproject.app.domain.model.CartModel
import com.example.firebaseproject.app.domain.model.ProductModel
import com.example.firebaseproject.app.domain.repository.CartRepository

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class CartRepositoryImpl(private val firestore: FirebaseFirestore) : CartRepository {

    override suspend fun getTotalProducts(userId: String, cartId: String): Int {
        val cartDoc = firestore.document(userId).get().await()
        return if (cartDoc.exists()) {
            val cart = cartDoc.toObject(CartModel::class.java)
            cart?.products?.sumOf { it.quantity } ?: 0
        } else {
            0
        }
    }

    override suspend fun shareCartWithEmail(currentUserId: String, email: String): Result<Unit> {
        return try {
            // Find recipient user by email
            val recipientQuery = firestore.collection("users")
                .whereEqualTo("email", email)
                .get()
                .await()

            if (recipientQuery.isEmpty) {
                Log.e("CartRepository", "User with email $email not found")
                return Result.failure(Exception("User with email $email not found"))
            }

            val recipientUserId = recipientQuery.documents.first().id

            // Fetch current user's cart
            val currentCartSnapshot = firestore.collection("carts")
                .document(currentUserId)
                .get()
                .await()

            if (!currentCartSnapshot.exists()) {
                Log.e("CartRepository", "Cart for user $currentUserId not found")
                return Result.failure(Exception("Cart for current user not found"))
            }

            val currentCart = currentCartSnapshot.toObject(CartDTO::class.java)
                ?: return Result.failure(Exception("Failed to parse current user's cart"))

            // Fetch recipient's cart
            val recipientCartSnapshot = firestore.collection("carts")
                .document(recipientUserId)
                .get()
                .await()

            if (recipientCartSnapshot.exists()) {
                // Merge carts if recipient already has one
                val recipientCart = recipientCartSnapshot.toObject(CartDTO::class.java)
                val updatedItems = currentCart.items.map { currentItem ->
                    val matchingItem = recipientCart?.items?.find { it.productId == currentItem.productId }
                    if (matchingItem != null) {
                        matchingItem.copy(quantity = matchingItem.quantity + currentItem.quantity)
                    } else {
                        CartItemModel(
                            productId = currentItem.productId,
                            quantity = currentItem.quantity
                        )
                    }
                }

                firestore.collection("carts").document(recipientUserId)
                    .update("items", updatedItems)
                    .await()
            } else {
                // Create a new cart for the recipient
                val newCart = CartDTO(userId = recipientUserId, items = currentCart.items)
                firestore.collection("carts").document(recipientUserId)
                    .set(newCart)
                    .await()
            }
            Log.d("CartRepository", "Cart shared successfully with $email")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("CartRepository", "Error sharing cart: ", e)
            Result.failure(e)
        }
    }

    override suspend fun addProductToCart(product: ProductModel, userId: String): Result<Unit> {
        val cartRef = firestore.collection("carts").document(userId)

        return try {
            val cartSnapshot = cartRef.get().await()

            if (cartSnapshot.exists()) {
                val currentCart = cartSnapshot.toObject(CartModel::class.java)
                val updatedProducts = currentCart?.products?.toMutableList() ?: mutableListOf()

                val productCollection = firestore.collection("products")
                val productSnapshot = productCollection.whereEqualTo("name", product.name).get().await()

                if (productSnapshot.isEmpty) {
                    Log.e("CartRepository", "Product not found in Firestore")
                    return Result.failure(Exception("Product not found"))
                }

                val productDocId = productSnapshot.documents[0].id

                val existingProduct = updatedProducts.find { it.productId == productDocId }

                if (existingProduct != null) {
                    val updatedProduct = existingProduct.copy(quantity = existingProduct.quantity + 1)
                    val index = updatedProducts.indexOf(existingProduct)
                    updatedProducts[index] = updatedProduct
                } else {
                    updatedProducts.add(CartItemModel(productDocId, 1))
                }

                cartRef.update("products", updatedProducts).await()
            } else {
                val productCollection = firestore.collection("products")
                val productSnapshot = productCollection.whereEqualTo("name", product.name).get().await()

                if (productSnapshot.isEmpty) {
                    Log.e("CartRepository", "Product not found in Firestore")
                    return Result.failure(Exception("Product not found"))
                }

                val productDocId = productSnapshot.documents[0].id
                val newCart = CartModel(userId, listOf(CartItemModel(productDocId, 1)))
                cartRef.set(newCart).await()
            }

            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("CartRepository", "Error while adding product to cart: ${e.message}", e)
            Result.failure(e)
        }
    }

    override suspend fun removeProductFromCart(productId: String, userId: String): Result<Unit> {
        val cartRef = firestore.collection("carts").document(userId)
        val cartDoc = cartRef.get().await()

        return try {
            if (cartDoc.exists()) {
                val currentCart = cartDoc.toObject(CartModel::class.java)
                val updatedProducts = currentCart?.products?.map { product ->
                    if (product.productId == productId && product.quantity > 1) {
                        product.copy(quantity = product.quantity - 1)
                    } else if (product.productId == productId && product.quantity == 1) {
                        null
                    } else {
                        product
                    }
                }?.filterNotNull() ?: emptyList()

                cartRef.update("products", updatedProducts).await()
            } else {
                Log.e("CartRepository", "Cart document does not exist for user: $userId")
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("CartRepository", "Error decrementing product quantity: ${e.message}", e)
            Result.failure(e)
        }
    }

    override suspend fun deleteCart(userId: String): Result<Unit> {
        val cartRef = firestore.collection("carts").document(userId)

        return try {
            cartRef.delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCart(userId: String): CartDTO? {
        val cartRef = firestore.collection("carts").document(userId)
        val productsRef = firestore.collection("products")

        return try {

            val cartDoc = cartRef.get().await()

            if (!cartDoc.exists()) {
                Log.d("CartRepository", "No cart found for user: $userId")
                return null
            }

            val cartModel = cartDoc.toObject(CartModel::class.java) ?: return null

            val cartItems = cartModel.products.mapNotNull { cartItem ->
                Log.d("CartRepository", "Fetching product details for productId: ${cartItem.productId}")
                val productDoc = productsRef.document(cartItem.productId).get().await()

                if (productDoc.exists()) {
                    val productData = productDoc.toObject(ProductModel::class.java)
                    if (productData != null) {
                        CartItemDTO(
                            productId = cartItem.productId,
                            productName = productData.name,
                            productPrice = productData.price,
                            quantity = cartItem.quantity
                        )
                    } else {
                        Log.d("CartRepository", "Product document exists, but data is null for productId: ${cartItem.productId}")
                        null
                    }
                } else {
                    Log.d("CartRepository", "Product not found for productId: ${cartItem.productId}")
                    null
                }
            }

            CartDTO(userId = cartModel.userId, items = cartItems)
        } catch (e: Exception) {
            Log.e("CartRepository", "Error while getting cart for user: $userId, error: ${e.message}", e)
            null
        }
    }

}


