package com.example.firebaseproject.app.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebaseproject.app.data.remote.api.FirebaseInstance
import com.example.firebaseproject.app.data.repository.CartRepositoryImpl
import com.example.firebaseproject.app.data.repository.ProductRepositoryImpl
import com.example.firebaseproject.app.domain.model.ProductModel
import com.example.firebaseproject.app.domain.use_case.cart.GetTotalCartQuantityUseCase
import com.example.firebaseproject.app.domain.use_case.cart.addProductCartUseCase
import com.example.firebaseproject.app.domain.use_case.cart.shareCartProductsUseCase
import com.example.firebaseproject.app.domain.use_case.products.getAllProductsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel() : ViewModel() {

    private val db = FirebaseInstance.firestore

    private val productRepository = ProductRepositoryImpl(db)
    private val cartRepository = CartRepositoryImpl(db)

    private val getAllProductsUseCase = getAllProductsUseCase(productRepository)
    private val addProductToCartUseCase = addProductCartUseCase(cartRepository)
    private val getTotalCartQuantityUseCase = GetTotalCartQuantityUseCase(productRepository)

    private val _products = MutableStateFlow<List<ProductModel>>(emptyList())
    val products: StateFlow<List<ProductModel>> get() = _products

    private val _totalQuantity = MutableStateFlow(0)
    val totalQuantity: StateFlow<Int> = _totalQuantity

    fun fetchTotalCartQuantity(userId: String) {
        viewModelScope.launch {
            val quantity = getTotalCartQuantityUseCase.execute(userId)
            _totalQuantity.value = quantity
        }
    }

    fun fetchProducts() {
        viewModelScope.launch {
            try {
                _products.value = getAllProductsUseCase()
            } catch (e: Exception) {
                println("Error fetching products: ${e.message}")
            }
        }
    }

    fun addProductToCart(product: ProductModel, userId: String) {
        viewModelScope.launch {
            try {
                addProductToCartUseCase(product, userId)
                fetchTotalCartQuantity(userId)
            } catch (e: Exception) {
                println("Error adding product to cart: ${e.message}")
            }
        }
    }

    init {
        fetchProducts()
    }
}