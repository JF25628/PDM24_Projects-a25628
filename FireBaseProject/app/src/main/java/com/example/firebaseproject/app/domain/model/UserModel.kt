package com.example.firebaseproject.app.domain.model

import com.example.firebaseproject.app.data.remote.model.AddUserDto

data class UserModel (
    val email: String,
    val password: String = "",
//    val carts: List<CartModel> = listOf()
){
    fun ToAddUserDto(): AddUserDto {
        return AddUserDto(
            //id = id,
            email = email,
            password = password
        )
    }
}