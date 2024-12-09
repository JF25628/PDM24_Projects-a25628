package com.example.firebaseproject.app.domain.use_case

import com.example.firebaseproject.app.domain.model.User
import com.example.firebaseproject.app.domain.repository.UserRepository

class LoginUserUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(email: String, password: String): User {
        return userRepository.loginUser(email, password)
    }
}