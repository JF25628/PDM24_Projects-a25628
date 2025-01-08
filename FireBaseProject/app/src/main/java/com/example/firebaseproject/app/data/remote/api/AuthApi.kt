package com.example.firebaseproject.app.data.remote.api

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface AuthApi {
    fun loginWithEmailAndPassword(email: String, password: String): Task<AuthResult>
    fun createUserWithEmailAndPassword(email: String, password: String): Task<AuthResult>
}