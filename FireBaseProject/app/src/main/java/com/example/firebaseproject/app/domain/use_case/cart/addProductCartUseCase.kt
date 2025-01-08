package com.example.firebaseproject.app.domain.use_case.cart

import com.example.firebaseproject.app.domain.model.ProductModel
import com.example.firebaseproject.app.domain.repository.CartRepository


class addProductCartUseCase(private val cartRepository: CartRepository) {
    suspend operator fun invoke(productId: ProductModel, userId: String): Result<Unit> = cartRepository.addProductToCart(productId, userId)
}