package com.example.firebaseproject.app.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.firebaseproject.app.data.remote.api.FirebaseInstance
import com.example.firebaseproject.app.data.remote.model.CartItemDTO
import com.example.firebaseproject.app.data.repository.CartRepositoryImpl
import com.example.firebaseproject.app.domain.use_case.cart.clearCartUseCase
import com.example.firebaseproject.app.domain.use_case.cart.getCartProductsUseCase
import com.example.firebaseproject.app.domain.use_case.cart.removeProductCartUseCase
import com.example.firebaseproject.app.domain.use_case.cart.shareCartProductsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class CartViewModelFactory(
    private val userId: String,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CartViewModel(userId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class CartViewModel(private val userId: String,) : ViewModel() {
    private val db = FirebaseInstance.firestore
    private val cartRepository = CartRepositoryImpl(db)

    private val getCartUseCase = getCartProductsUseCase(cartRepository)
    private val shareCartUseCase = shareCartProductsUseCase(cartRepository)
    private val removeProductCartUseCase = removeProductCartUseCase(cartRepository)
    private val clearCartUseCase = clearCartUseCase(cartRepository)

    private val _cartState = MutableStateFlow<Result<Unit>>(Result.success(Unit))
    val cartState: StateFlow<Result<Unit>> get() = _cartState

    private val _cartProducts = MutableStateFlow<List<CartItemDTO>>(emptyList())
    val cartProducts: StateFlow<List<CartItemDTO>> get() = _cartProducts


    init {
        fetchCart()
    }

    fun fetchCart() {
        viewModelScope.launch {
            val cart = getCartUseCase(userId)
            if (cart != null) {
                _cartProducts.value = cart.items
            } else {
                _cartProducts.value = emptyList()
            }
        }
    }

    fun calculateTotal(): Double {
        return _cartProducts.value.sumOf { it.productPrice * it.quantity }
    }

    fun deleteCart() {
        viewModelScope.launch {
            clearCartUseCase(userId)
            _cartProducts.value = emptyList()
        }
    }

    fun shareCartWithEmail(email: String) {
        viewModelScope.launch {
            val result = shareCartUseCase(userId, email)
            if (result.isSuccess) {
                Log.d("CartViewModel", "Cart shared successfully with $email")
            } else {
                Log.e("CartViewModel", "Failed to share cart: ${result.exceptionOrNull()}")
            }
        }
    }

    fun removeProduct(productId: String) {
        viewModelScope.launch {
            val result = removeProductCartUseCase(productId, userId)
            if (result.isSuccess) {
                fetchCart()
            } else {
                Log.e("CartViewModel", "Failed to remove product: ${result.exceptionOrNull()}")
            }
        }
    }
}