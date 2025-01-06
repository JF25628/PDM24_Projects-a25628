package com.example.firebaseproject.app.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.firebaseproject.app.domain.model.UserModel
import com.example.firebaseproject.app.presentation.viewModel.UserViewModel

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: UserViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
//    var name by remember { mutableStateOf("") }
//    var email by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
//    val authState by viewModel.authState.collectAsState()
//
//    Column(
//        modifier = Modifier.fillMaxSize().padding(16.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        TextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
//        Spacer(modifier = Modifier.height(8.dp))
//        TextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
//        Spacer(modifier = Modifier.height(8.dp))
//        TextField(value = password, onValueChange = { password = it }, label = { Text("Password") }, visualTransformation = PasswordVisualTransformation())
//        Spacer(modifier = Modifier.height(16.dp))
//        Button(onClick = { viewModel.register(email, password) }) {
//            Text("Register")
//        }
//
////        if (authState.isSuccess) {
////            onRegisterSuccess()
////        } else if (authState.isFailure) {
////            Text("Registration Failed: ${authState.exceptionOrNull()?.message}", color = Color.Red)
////        }
//    }


    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }

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
            Text(text = "Registrar", style = MaterialTheme.typography.headlineMedium)

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
                onClick = {
                    val user = UserModel(email = email, password = password)
                    viewModel.register(
                        user = user,
                        onSuccess = {
                            successMessage = "Registo concluído com sucesso!"
                            navController.navigate("login")
                        },
                        onError = { exception ->
                            errorMessage = exception.message ?: "Erro desconhecido"
                        }
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar")
            }

            errorMessage?.let {
                Text(text = "Erro: $it", color = MaterialTheme.colorScheme.error)
            }

            successMessage?.let {
                Text(text = it, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

