package com.example.firebaseproject.app.presentation.screen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import com.example.firebaseproject.app.presentation.viewModel.UserViewModel
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: UserViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val loginState by viewModel.loginState.collectAsState()

    // Observe login state to handle navigation or show error messages
    LaunchedEffect(loginState) {
        when (loginState) {
            is UserViewModel.LoginState.Success -> {
                // Navigate to the products screen upon successful login
                navController.navigate("products") {
                    popUpTo("login") { inclusive = true }
                }
            }
            is UserViewModel.LoginState.Error -> {
                // Handle login failure (e.g., show a message)
                // You can use Snackbar or Text to show the error message
                Log.e("Login", (loginState as UserViewModel.LoginState.Error).message)
            }
            else -> {
                // Handle other states if needed
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = { viewModel.updateEmail(it) },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = password,
                onValueChange = { viewModel.updatePassword(it) },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )
            Button(
                onClick = { viewModel.login(email, password) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }

            TextButton(
                onClick = { navController.navigate("register") }
            ) {
                Text("Criar uma conta")
            }

            if (loginState is UserViewModel.LoginState.Loading) {
                CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
            }
        }
    }
}

