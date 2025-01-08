package com.example.firebaseproject.app.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebaseproject.app.data.remote.api.AuthApi
import com.example.firebaseproject.app.data.remote.api.FirebaseInstance.auth
import com.example.firebaseproject.app.data.repository.AuthRepositoryImpl
import com.example.firebaseproject.app.domain.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class UserViewModel : ViewModel() {
    private val dbAuth = Firebase.auth
    private val authApi: AuthApi = AuthRepositoryImpl(dbAuth)

    private val _email = MutableStateFlow("")
    private val _password = MutableStateFlow("")
    val email: StateFlow<String> = _email
    val password: StateFlow<String> = _password

    sealed class LoginState {
        object Idle : LoginState()
        object Loading : LoginState()
        object Success : LoginState()
        data class Error(val message: String) : LoginState()
    }

    private val _currentUser = MutableStateFlow<FirebaseUser?>(auth.currentUser)
    val currentUser: StateFlow<FirebaseUser?> get() = _currentUser

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> get() = _loginState


    private val authStateListener = FirebaseAuth.AuthStateListener { auth ->
        _currentUser.value = auth.currentUser
    }

    init {
        auth.addAuthStateListener(authStateListener)
    }

    override fun onCleared() {
        super.onCleared()
        auth.removeAuthStateListener(authStateListener)
    }

    fun login(email: String, password: String) {
        _loginState.value = LoginState.Loading

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _currentUser.value = auth.currentUser
                    _loginState.value = LoginState.Success
                    Log.d("UserViewModel", "Login successful")
                } else {
                    _loginState.value = LoginState.Error(task.exception?.message ?: "Login failed")
                    Log.e("UserViewModel", "Login failed: ${task.exception?.message}")
                }
            }
    }


    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    fun register(user: UserModel, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        viewModelScope.launch {
            try {
                val authResult = authApi.createUserWithEmailAndPassword(user.email, user.password).await()
                val firebaseUser = authResult.user

                if (firebaseUser != null) {
                    user.ToAddUserDto()
                    dbAuth.signOut()
                    onSuccess()
                }
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    fun logout() {
        try {
            Log.d("UserViewModel", "Logging out user")
            dbAuth.signOut()
            _currentUser.value = null
            Log.d("UserViewModel", "User logged out successfully")
        } catch (e: Exception) {
            Log.e("UserViewModel", "Logout failed", e)
        }
    }

}
