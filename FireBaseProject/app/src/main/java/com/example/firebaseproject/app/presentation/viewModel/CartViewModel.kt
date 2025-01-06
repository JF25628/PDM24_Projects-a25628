package com.example.firebaseproject.app.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.firebaseproject.app.data.remote.api.FirebaseInstance
import com.example.firebaseproject.app.data.remote.model.CartDTO
import com.example.firebaseproject.app.data.remote.model.CartItemDTO
import com.example.firebaseproject.app.data.repository.CartRepositoryImpl
import com.example.firebaseproject.app.domain.model.CartItemModel
import com.example.firebaseproject.app.domain.model.CartModel
import com.example.firebaseproject.app.domain.use_case.cart.getCartProductsUseCase
import com.google.firebase.firestore.FieldValue
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
                _cartProducts.value = cart.items // Update the state with fetched cart items
            } else {
                _cartProducts.value = emptyList() // If no cart is found, set an empty list
            }
        }
    }

    // Calculate total price of cart items
    fun calculateTotal(): Double {
        return _cartProducts.value.sumOf { it.productPrice * it.quantity }
    }

    // Delete the cart after purchase
    fun deleteCart() {
        viewModelScope.launch {
            cartRepository.deleteCart(userId) // Remove cart from the repository
            _cartProducts.value = emptyList() // Clear cart from the UI
        }
    }

    fun shareCartWithEmail(email: String) {
        // Check if the user with the given email has a cart
        db.collection("users")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.isEmpty) {
                    Log.d("CartViewModel", "User with email $email not found")
                    // Optionally handle user not found, e.g., show a message or create the user
                    return@addOnSuccessListener
                }

                // Get the recipient user ID
                val recipientUserId = querySnapshot.documents.first().id

                // Create or update the cart for the recipient
                val cartRef = db.collection("carts").document(recipientUserId)
                cartRef.get().addOnSuccessListener { document ->
                    if (document.exists()) {
                        // If the cart already exists, update it
                        val existingProducts = document.get("products") as? List<Map<String, Any>> ?: emptyList()
                        val updatedProducts = existingProducts.toMutableList()

                        // Convert _cartProducts to CartItemModel and add to updatedProducts
                        _cartProducts.value.forEach { product ->
                            val cartItem = CartItemModel(
                                productId = product.productId,
                                quantity = product.quantity
                            )
                            updatedProducts.add(mapOf(
                                "productId" to cartItem.productId,
                                "quantity" to cartItem.quantity
                            ))
                        }

                        cartRef.update("products", FieldValue.arrayUnion(*updatedProducts.toTypedArray()))
                    } else {
                        // Create a new cart if it doesn't exist
                        val newCart = CartModel(userId = recipientUserId, products = _cartProducts.value.map { product ->
                            CartItemModel(
                                productId = product.productId,
                                quantity = product.quantity
                            )
                        })
                        cartRef.set(newCart)
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.e("CartViewModel", "Error sharing cart: ", exception)
            }
    }

}