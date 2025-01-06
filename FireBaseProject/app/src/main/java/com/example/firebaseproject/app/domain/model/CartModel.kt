package com.example.firebaseproject.app.domain.model

import com.example.firebaseproject.app.data.remote.model.AddEditCartDto

data class CartModel (
    val userId: String = "",
    val products: List<CartItemModel> = listOf()
){
    fun toAddEditCartDto(): AddEditCartDto {
        return AddEditCartDto(
            products = products
        )
    }
}
