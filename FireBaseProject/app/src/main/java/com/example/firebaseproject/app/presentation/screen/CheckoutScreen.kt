package com.example.firebaseproject.app.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.firebaseproject.app.presentation.viewModel.CartViewModel
import com.example.firebaseproject.app.presentation.viewModel.CartViewModelFactory
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun CheckoutScreen(
    navController: NavController
) {
    var selectedOption by remember { mutableStateOf("Credit Card") }
    val paymentOptions = listOf("Credit Card", "PayPal", "Google Pay")

    val userId = Firebase.auth.currentUser?.uid ?: ""

    val viewModel: CartViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = CartViewModelFactory(userId)
    )

    // Calculate total price of the items in the cart
    val totalPrice = viewModel.calculateTotal()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Select Payment Method",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Radio buttons for payment options
        paymentOptions.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                RadioButton(
                    selected = (option == selectedOption),
                    onClick = { selectedOption = option }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = option, style = MaterialTheme.typography.bodyLarge)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Display total price
        Text(
            text = "Total: ${"%.2f".format(totalPrice)}",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Confirm payment button
        Button(
            onClick = {
                viewModel.deleteCart()
                //navController.popBackStack()
                navController.navigate("products")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Confirm Payment")
        }
    }
}

