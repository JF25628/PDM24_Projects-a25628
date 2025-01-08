package com.example.firebaseproject.app.domain.use_case.cart

import com.example.firebaseproject.app.domain.repository.CartRepository

class removeProductCartUseCase(private val cartRepository: CartRepository) {
    suspend operator fun invoke(productId: String, userId: String): Result<Unit> =
        cartRepository.removeProductFromCart(productId, userId)
}