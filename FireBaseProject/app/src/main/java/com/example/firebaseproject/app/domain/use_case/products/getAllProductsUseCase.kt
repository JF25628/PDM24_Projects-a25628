package com.example.firebaseproject.app.domain.use_case.products

import com.example.firebaseproject.app.domain.model.ProductModel
import com.example.firebaseproject.app.domain.repository.ProductRepository

class getAllProductsUseCase(private val productRepository: ProductRepository) {
    suspend operator fun invoke(): List<ProductModel> = productRepository.getAllProducts()
}