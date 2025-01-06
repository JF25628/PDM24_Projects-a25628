package com.example.firebaseproject.app.domain.use_case.user

import com.example.firebaseproject.app.data.remote.api.AuthApi
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

class loginUseCase(private val userRepository: AuthApi) {
    suspend operator fun invoke(email: String, password: String): Task<AuthResult> = userRepository.loginWithEmailAndPassword(email, password)
}