package com.example.firebaseproject.app.domain.use_case.cart

import com.example.firebaseproject.app.domain.repository.ProductRepository

class GetTotalCartQuantityUseCase(private val productRepository: ProductRepository) {
    suspend fun execute(userId: String): Int {
        return productRepository.getCartItems(userId)
    }
}