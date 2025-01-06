package com.example.firebaseproject.app.domain.use_case.cart

import com.example.firebaseproject.app.domain.repository.CartRepository


class shareCartProductsUseCase(private val cartRepository: CartRepository) {
    suspend operator fun invoke(cartId: String, targetUserId: String): Result<Unit> {
        // Implement sharing logic, e.g., updating Firestore
        return Result.success(Unit)
    }
}