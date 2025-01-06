package com.example.firebaseproject.app.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.firebaseproject.app.presentation.viewModel.UserViewModel


@Composable
fun LogOutButton(navController: NavController, viewModel: UserViewModel) {
    BottomAppBar(
        modifier = Modifier.fillMaxWidth() // Make the bottom bar span the full width of the screen
    ) {
        Button(
            onClick = {
                // Log the user out
                viewModel.logout()

                // Navigate to the login screen and clear the previous screens from the stack
                navController.navigate("login") {
                    popUpTo("login") { inclusive = true }
                    launchSingleTop = true
                }
            }
        ) {
            Text(text = "Log Out")
        }
    }
}