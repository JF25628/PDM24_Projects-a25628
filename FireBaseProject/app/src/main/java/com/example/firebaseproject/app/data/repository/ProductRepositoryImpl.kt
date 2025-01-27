package com.example.firebaseproject.app.data.repository

import com.example.firebaseproject.app.domain.model.CartModel
import com.example.firebaseproject.app.domain.model.ProductModel
import com.example.firebaseproject.app.domain.repository.ProductRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ProductRepositoryImpl(private val firestore: FirebaseFirestore) : ProductRepository {
    override suspend fun getAllProducts(): List<ProductModel> {
        val result = firestore.collection("products").get().await()
        return result.documents.map { doc ->
            ProductModel(
                name = doc.getString("name") ?: "",
                price = doc.getDouble("price") ?: 0.0,
                description = doc.getString("description") ?: ""
            )
        }
    }

    override suspend fun getCartItems(userId: String): Int {
        val result = firestore.collection("carts")
            .whereEqualTo("userId", userId)
            .get()
            .await()

        val CartModel = result.documents.firstOrNull()?.toObject(CartModel::class.java)

        return CartModel?.products?.sumOf { it.quantity } ?: 0
    }

}
