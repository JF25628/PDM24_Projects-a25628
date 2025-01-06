package com.example.firebaseproject.app.domain.repository

import com.example.firebaseproject.app.domain.model.ProductModel

interface ProductRepository {
    suspend fun getAllProducts(): List<ProductModel>
    suspend fun getCartItems(userId: String): Int
}