package com.example.firebaseproject.app.presentation.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.firebaseproject.app.presentation.screen.CartScreen
import com.example.firebaseproject.app.presentation.screen.CheckoutScreen
import com.example.firebaseproject.app.presentation.screen.LoginScreen
import com.example.firebaseproject.app.presentation.screen.ProductListScreen
import com.example.firebaseproject.app.presentation.screen.RegisterScreen
import com.example.firebaseproject.app.presentation.viewModel.CartViewModel
import com.example.firebaseproject.app.presentation.viewModel.ProductViewModel
import com.example.firebaseproject.app.presentation.viewModel.UserViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun Navigation(
    navController: NavHostController
) {
    val userId = Firebase.auth.currentUser?.uid
    val startPage = if (userId.isNullOrEmpty()) "login" else "products"

//    // Launch navigation with the appropriate start page
//    LaunchedEffect(userId) {
//        // This ensures the navigation starts with the correct screen
//        if (userId == null) {
//            navController.navigate("login") {
//                // If the user is not logged in, clear the back stack to prevent navigation back to the product list
//                popUpTo("login") { inclusive = true }
//            }
//        } else {
//            navController.navigate("products") {
//                // If the user is logged in, clear the back stack to prevent navigation back to the login screen
//                popUpTo("login") { inclusive = true }
//            }
//        }
//    }

    NavHost(navController = navController, startDestination = startPage) {
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("products") {ProductListScreen(navController)}
        composable("cart") { CartScreen(navController) }
        composable("checkout") { CheckoutScreen(navController) }
    }
}

