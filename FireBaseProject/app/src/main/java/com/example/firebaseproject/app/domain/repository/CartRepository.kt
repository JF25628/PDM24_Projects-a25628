package com.example.firebaseproject.app.domain.repository

import com.example.firebaseproject.app.data.remote.model.CartDTO
import com.example.firebaseproject.app.domain.model.CartItemModel
import com.example.firebaseproject.app.domain.model.CartModel
import com.example.firebaseproject.app.domain.model.ProductModel

interface CartRepository {
    suspend fun getTotalProducts(userId: String, cartId: String): Int
    suspend fun shareCartProducts(emailOtherUser: String, cartModel: CartModel): Result<Unit>
    suspend fun addProductToCart(productId: ProductModel, userId: String): Result<Unit>
    suspend fun removeProductFromCart(productId: String, userId: String): Result<Unit>
    suspend fun deleteCart(userId: String): Result<Unit>
    suspend fun getCart(userId: String): CartDTO?
}