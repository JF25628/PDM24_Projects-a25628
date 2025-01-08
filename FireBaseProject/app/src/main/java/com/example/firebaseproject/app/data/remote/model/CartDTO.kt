package com.example.firebaseproject.app.data.remote.model

data class CartDTO(
    val userId: String,
    val items: List<CartItemDTO>
)

data class CartItemDTO(
    val productId: String,
    val productName: String,
    val productPrice: Double,
    val quantity: Int
)