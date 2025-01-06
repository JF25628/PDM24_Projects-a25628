package com.example.firebaseproject.app.presentation.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.firebaseproject.app.presentation.screen.CartScreen
import com.example.firebaseproject.app.presentation.screen.LoginScreen
import com.example.firebaseproject.app.presentation.screen.ProductListScreen
import com.example.firebaseproject.app.presentation.screen.RegisterScreen
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun Navigation(
    navController: NavHostController
) {
    val userId = Firebase.auth.currentUser?.uid
    val startPage = if (userId.isNullOrEmpty()) "login" else "products"

    NavHost(navController = navController, startDestination = startPage) {
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("products") {ProductListScreen(navController)}
        composable("cart") { CartScreen(navController) }
    }
}

