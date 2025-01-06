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
        // Retrieve the cart document using userId
        val cartDoc = firestore.document(userId).get().await()
        return if (cartDoc.exists()) {
            val cart = cartDoc.toObject(CartModel::class.java)
            cart?.products?.sumOf { it.quantity } ?: 0
        } else {
            0
        }
    }

    override suspend fun shareCartProducts(emailOtherUser: String, cartModel: CartModel): Result<Unit> {
        // Share a cart with another user by adding the products to their cart
        return try {
            val otherUserCartRef = firestore.document(emailOtherUser)
            otherUserCartRef.set(cartModel).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addProductToCart(product: ProductModel, userId: String): Result<Unit> {
        val cartRef = firestore.collection("carts").document(userId)

        return try {
            // Log the incoming parameters to verify
            Log.d("CartRepository", "addProductToCart called with userId: $userId")

            // Fetch the cart document
            val cartSnapshot = cartRef.get().await()
            Log.d("CartRepository", "Cart document fetched, exists: ${cartSnapshot.exists()}")

            if (cartSnapshot.exists()) {
                // Retrieve existing cart
                val currentCart = cartSnapshot.toObject(CartModel::class.java)
                val updatedProducts = currentCart?.products?.toMutableList() ?: mutableListOf()

                // Get the product document ID (Firestore automatically generates this ID)
                val productCollection = firestore.collection("products")
                val productSnapshot = productCollection.whereEqualTo("name", product.name).get().await()

                if (productSnapshot.isEmpty) {
                    Log.e("CartRepository", "Product not found in Firestore")
                    return Result.failure(Exception("Product not found"))
                }

                // Assuming the product collection has only one matching document
                val productDocId = productSnapshot.documents[0].id
                Log.d("CartRepository", "Product document ID: $productDocId")

                // Check if the product already exists in the cart
                val existingProduct = updatedProducts.find { it.productId == productDocId }
                Log.d("CartRepository", "Existing product found: ${existingProduct != null}")

                if (existingProduct != null) {
                    // Update the quantity of the existing product
                    val updatedProduct = existingProduct.copy(quantity = existingProduct.quantity + 1)
                    val index = updatedProducts.indexOf(existingProduct)
                    updatedProducts[index] = updatedProduct
                    Log.d("CartRepository", "Product updated with new quantity: ${updatedProduct.quantity}")
                } else {
                    // Add the new product to the cart
                    updatedProducts.add(CartItemModel(productDocId, 1))
                    Log.d("CartRepository", "New product added: $productDocId")
                }

                // Update the cart document with the updated products
                cartRef.update("products", updatedProducts).await()
                Log.d("CartRepository", "Cart updated successfully.")
            } else {
                // Create a new cart document if it doesn't exist, use the productDocId for the new cart item
                val productCollection = firestore.collection("products")
                val productSnapshot = productCollection.whereEqualTo("name", product.name).get().await()

                if (productSnapshot.isEmpty) {
                    Log.e("CartRepository", "Product not found in Firestore")
                    return Result.failure(Exception("Product not found"))
                }

                val productDocId = productSnapshot.documents[0].id
                val newCart = CartModel(userId, listOf(CartItemModel(productDocId, 1)))
                cartRef.set(newCart).await()
                Log.d("CartRepository", "New cart created and product added.")
            }

            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("CartRepository", "Error while adding product to cart: ${e.message}", e)
            Result.failure(e)
        }
    }

    override suspend fun removeProductFromCart(productId: String, userId: String): Result<Unit> {
        // Reference to the user's cart
        val cartRef = firestore.collection("carts").document(userId)
        val cartDoc = cartRef.get().await()

        return try {
            if (cartDoc.exists()) {
                val currentCart = cartDoc.toObject(CartModel::class.java)
                val updatedProducts = currentCart?.products?.filter { it.productId != productId } ?: emptyList()

                // Update the cart with the filtered list of products (excluding the removed product)
                cartRef.update("products", updatedProducts).await()
                Log.d("CartRepository", "Product removed successfully: $productId")
            } else {
                Log.e("CartRepository", "Cart document does not exist for user: $userId")
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("CartRepository", "Error removing product from cart: ${e.message}", e)
            Result.failure(e)
        }
    }


    override suspend fun deleteCart(userId: String): Result<Unit> {
        // Reference to the Firestore collection that stores the carts
        val cartRef = firestore.collection("carts").document(userId)

        return try {
            // Attempt to delete the document for the given user ID
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
            // Log the start of the function and the userId being used
            Log.d("CartRepository", "getCart called with userId: $userId")

            // Fetch the cart document
            val cartDoc = cartRef.get().await()
            Log.d("CartRepository", "Cart document fetched, exists: ${cartDoc.exists()}")

            if (!cartDoc.exists()) {
                Log.d("CartRepository", "No cart found for user: $userId")
                return null
            }

            // Convert cart document to CartModel
            val cartModel = cartDoc.toObject(CartModel::class.java) ?: return null
            Log.d("CartRepository", "Cart document converted to CartModel, userId: ${cartModel.userId}")

            // Fetch detailed product info using product document IDs
            val cartItems = cartModel.products.mapNotNull { cartItem ->
                Log.d("CartRepository", "Fetching product details for productId: ${cartItem.productId}")
                val productDoc = productsRef.document(cartItem.productId).get().await() // Fetch product by document ID

                if (productDoc.exists()) {
                    val productData = productDoc.toObject(ProductModel::class.java)
                    if (productData != null) {
                        Log.d("CartRepository", "Product found: ${productData.name}, price: ${productData.price}")
                        // Map cart item to CartItemDTO using the product document ID
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

            // Log the result before returning the DTO
            Log.d("CartRepository", "Returning CartDTO with ${cartItems.size} items.")
            // Return the DTO with all data combined
            CartDTO(userId = cartModel.userId, items = cartItems)
        } catch (e: Exception) {
            Log.e("CartRepository", "Error while getting cart for user: $userId, error: ${e.message}", e)
            null // Return null if any exception occurs
        }
    }

}


