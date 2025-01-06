package com.example.firebaseproject.app.data.repository

import android.util.Log
import com.example.firebaseproject.app.data.remote.api.AuthApi
import com.example.firebaseproject.app.data.remote.api.FirebaseInstance.firestore
import com.example.firebaseproject.app.domain.model.UserModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class AuthRepositoryImpl(private val auth: FirebaseAuth) : AuthApi {
    override fun loginWithEmailAndPassword(email: String, password: String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }

    override fun createUserWithEmailAndPassword(email: String, password: String): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, password).apply {
            addOnSuccessListener { authResult ->
                val user = authResult.user
                user?.let {
                    val userModel = UserModel(
                        email = it.email ?: "",
                    )

                    val userRef = firestore.collection("users").document(it.uid)

                    val userData = hashMapOf(
                        "email" to userModel.email,
                    )

                    userRef.set(userData)
                        .addOnSuccessListener {
                            Log.d("Firebase", "User profile created in Firestore")
                        }
                        .addOnFailureListener { exception ->
                            Log.e("Firebase", "Error creating user profile in Firestore", exception)
                        }
                }
            }
            addOnFailureListener { exception ->
                Log.e("Firebase", "User registration failed", exception)
            }
        }
    }
}
