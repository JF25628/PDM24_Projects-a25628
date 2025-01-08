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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.ui.Alignment

@OptIn(ExperimentalMaterial3Api::class)
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
    val totalPrice = viewModel.calculateTotal()

    LaunchedEffect(cartProducts) {
        Log.d("CartScreen", "Cart products: $cartProducts")
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Your Cart") },
                actions = {
                    IconButton(onClick = { showEmailInput = !showEmailInput }) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share Cart"
                        )
                    }
                    IconButton(
                        onClick = {
                            viewModel.deleteCart()
                            navController.navigate("products")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Cart"
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Total: ${"%.2f".format(totalPrice)}€",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Button(
                        onClick = {
                            viewModel.deleteCart()
                            navController.navigate("products")
                        },
                        modifier = Modifier.padding(start = 16.dp)
                    ) {
                        Text(text = "Confirm Payment")
                    }
                }
            }

        }

    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = paddingValues.calculateTopPadding())
            ) {
                if (showEmailInput) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    ) {
                        TextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text("Enter email") },
                            isError = emailError.isNotEmpty(),
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = {
                                if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                                    emailError = "Invalid email."
                                } else {
                                    emailError = ""
                                    viewModel.shareCartWithEmail(email)
                                    showEmailInput = false
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = "Send Cart"
                            )
                        }
                    }
                    if (emailError.isNotEmpty()) {
                        Text(
                            text = emailError,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                if (cartProducts.isEmpty()) {
                    Text("Your cart is empty!", style = MaterialTheme.typography.bodyMedium)
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = paddingValues.calculateTopPadding())
                    ) {
                        items(cartProducts) { product ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(product.productName, style = MaterialTheme.typography.bodyLarge)
                                    Text(
                                        "${product.productPrice}€ x ${product.quantity}",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                IconButton(
                                    onClick = {
                                        viewModel.removeProduct(product.productId)
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Remove Product"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetchCart()
    }
}