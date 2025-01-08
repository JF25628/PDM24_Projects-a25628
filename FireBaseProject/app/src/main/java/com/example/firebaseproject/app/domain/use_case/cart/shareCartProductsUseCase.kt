package com.example.firebaseproject.app.domain.use_case.cart

import com.example.firebaseproject.app.domain.repository.CartRepository


class shareCartProductsUseCase(private val cartRepository: CartRepository) {
    suspend operator fun invoke(currentUserId: String, email: String): Result<Unit> {
        return cartRepository.shareCartWithEmail(currentUserId, email)
    }
}