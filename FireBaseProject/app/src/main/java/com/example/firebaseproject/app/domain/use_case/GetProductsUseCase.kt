package com.example.firebaseproject.app.domain.use_case

import com.example.firebaseproject.app.domain.model.Product
import com.example.firebaseproject.app.domain.repository.ProductRepository

class GetProductsUseCase(private val productRepository: ProductRepository) {
    suspend operator fun invoke(): List<Product>{
        return productRepository.getAllProducts()
    }
}