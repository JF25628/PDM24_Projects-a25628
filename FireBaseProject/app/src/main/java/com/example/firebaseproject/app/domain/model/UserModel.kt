package com.example.firebaseproject.app.domain.model

import com.example.firebaseproject.app.data.remote.model.AddUserDto

data class UserModel (
    val email: String,
    val password: String = "",
){
    fun ToAddUserDto(): AddUserDto {
        return AddUserDto(
            email = email,
            password = password
        )
    }
}