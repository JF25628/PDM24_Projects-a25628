package com.example.firebaseproject.app.domain.model

import com.example.firebaseproject.app.data.remote.model.AddEditCartItemDto

data class CartItemModel(
    val productId: String = "",
    val quantity: Int = 0
) {
    fun toAddEditCartItemDto(): AddEditCartItemDto {
        return AddEditCartItemDto(
            productId = productId,
            quantity = quantity
        )
    }
}
