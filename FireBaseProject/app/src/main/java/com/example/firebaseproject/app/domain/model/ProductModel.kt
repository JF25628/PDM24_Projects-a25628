package com.example.firebaseproject.app.domain.model

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

data class ProductModel(
    val name: String = "",
    val description: String = "",
    val price: Double = 0.0
)

