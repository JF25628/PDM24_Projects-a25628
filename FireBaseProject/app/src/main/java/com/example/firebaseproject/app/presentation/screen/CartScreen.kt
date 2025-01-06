package com.example.firebaseproject.app.presentation.screen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.firebaseproject.app.presentation.viewModel.CartViewModel
import com.example.firebaseproject.app.presentation.viewModel.CartViewModelFactory
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun CartScreen(
    navController: NavController,
) {
    val userId = Firebase.auth.currentUser?.uid ?: ""

    val viewModel: CartViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = CartViewModelFactory(userId)
    )

    val cartProducts by viewModel.cartProducts.collectAsState()
    var email by remember { mutableStateOf("") }
    var showEmailInput by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf("") }

    // Add some logging to ensure cartProducts is populated
    LaunchedEffect(cartProducts) {
        Log.d("CartScreen", "Cart products: $cartProducts")
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomAppBar(
                content = {
                    Button(
                        onClick = {
                            navController.navigate("checkout")
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Checkout")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(bottom = paddingValues.calculateBottomPadding()) // Ensure bottom padding for the bottom bar
        ) {
            Text("Your Cart", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))

            if (cartProducts.isEmpty()) {
                Text("Your cart is empty!", style = MaterialTheme.typography.bodyMedium)
            } else {
                LazyColumn {
                    items(cartProducts) { product ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(product.productName)
                            Text("$${product.productPrice} x${product.quantity}")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Share button
            Button(
                onClick = { showEmailInput = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Share Cart")
            }

            // Email input and send button visibility
            if (showEmailInput) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) {
                    TextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Enter email") },
                        isError = emailError.isNotEmpty(),
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (emailError.isNotEmpty()) {
                        Text(
                            text = emailError,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            // Validate email
                            if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                                emailError = "Please enter a valid email."
                            } else {
                                emailError = ""
                                // Call the function to share the cart
                                viewModel.shareCartWithEmail(email)
                                showEmailInput = false // Hide the email input after sharing
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Send Cart")
                    }
                }
            }
        }
    }

    // Trigger cart fetching
    LaunchedEffect(Unit) {
        viewModel.fetchCart()
    }
}


