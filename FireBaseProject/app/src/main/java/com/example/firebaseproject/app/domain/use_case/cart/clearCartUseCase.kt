package com.example.firebaseproject.app.domain.use_case.cart

import com.example.firebaseproject.app.domain.repository.CartRepository

class clearCartUseCase(private val cartRepository: CartRepository) {
    suspend operator fun invoke(userId: String): Result<Unit> = cartRepository.deleteCart(userId)
}