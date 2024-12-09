package com.example.firebaseproject.app.domain.repository

import com.example.firebaseproject.app.domain.model.Product

interface ProductRepository {
    suspend fun getAllProducts(): List<Product>
    suspend fun getProductDetails(): Product
}