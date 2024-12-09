package com.example.firebaseproject.app.domain.repository

import com.example.firebaseproject.app.domain.model.User

interface UserRepository {
    suspend fun loginUser(email: String, password: String): User
    suspend fun registerUser(name: String, email: String, password: String): User
    suspend fun getUserDetails(userId: String): User
}