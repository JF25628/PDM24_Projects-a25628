package com.example.firebaseproject.app.data.remote.model

import com.example.firebaseproject.app.domain.model.CartItemModel

data class AddEditCartDto(
    val products: List<CartItemModel>
)
