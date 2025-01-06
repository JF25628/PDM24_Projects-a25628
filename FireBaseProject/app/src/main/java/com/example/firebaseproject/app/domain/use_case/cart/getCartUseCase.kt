package com.example.firebaseproject.app.domain.use_case.cart

import com.example.firebaseproject.app.data.remote.model.CartDTO
import com.example.firebaseproject.app.domain.repository.CartRepository

class getCartUseCase(private val cartRepository: CartRepository) {
    suspend operator fun invoke(userId: String): CartDTO? = cartRepository.getCart(userId)
}