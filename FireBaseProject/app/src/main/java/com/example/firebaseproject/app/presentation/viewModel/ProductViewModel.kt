package com.example.firebaseproject.app.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebaseproject.app.data.remote.api.FirebaseInstance
import com.example.firebaseproject.app.data.repository.CartRepositoryImpl
import com.example.firebaseproject.app.data.repository.ProductRepositoryImpl
import com.example.firebaseproject.app.domain.model.ProductModel
import com.example.firebaseproject.app.domain.use_case.cart.addProductCartUseCase
import com.example.firebaseproject.app.domain.use_case.products.getAllProductsUseCase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ProductViewModel() : ViewModel() {

    private val db = FirebaseInstance.firestore

    // Repositories
    private val productRepository = ProductRepositoryImpl(db)
    private val cartRepository = CartRepositoryImpl(db)

    // Use Cases
    private val getAllProductsUseCase = getAllProductsUseCase(productRepository)
    private val addProductToCartUseCase = addProductCartUseCase(cartRepository)

    // State
    private val _products = MutableStateFlow<List<ProductModel>>(emptyList())
    val products: StateFlow<List<ProductModel>> get() = _products

    // Fetch products on initialization


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
                println("Product added to cart: ${product.name}")
            } catch (e: Exception) {
                println("Error adding product to cart: ${e.message}")
            }
        }
    }


//
//    val productsList = listOf(
//        ProductModel(name = "Samsung Galaxy S23", description = "Latest Samsung Galaxy phone with cutting-edge features", price = 799.99),
//        ProductModel(name = "Apple MacBook Pro 16-inch", description = "Powerful laptop with Apple's M1 Pro chip", price = 2399.99),
//        ProductModel(name = "Sony WH-1000XM5", description = "Industry-leading noise-canceling headphones", price = 348.00),
//        ProductModel(name = "Nintendo Switch OLED", description = "Portable gaming console with OLED screen", price = 349.99),
//        ProductModel(name = "GoPro HERO11", description = "Waterproof action camera with high-definition video", price = 499.99),
//        ProductModel(name = "Apple Watch Series 8", description = "Smartwatch with health tracking and GPS", price = 399.99),
//        ProductModel(name = "Sony 65-inch 4K TV", description = "Ultra HD 4K television with HDR and smart features", price = 899.99),
//        ProductModel(name = "Dyson V15 Detect", description = "Cordless vacuum cleaner with laser illumination", price = 699.99),
//        ProductModel(name = "iPad Pro 12.9-inch", description = "Tablet with powerful M1 chip and stunning Liquid Retina display", price = 1099.99),
//        ProductModel(name = "Bose QuietComfort 45", description = "Noise-canceling headphones with exceptional sound quality", price = 329.99),
//        ProductModel(name = "Canon EOS R6", description = "Full-frame mirrorless camera with 4K video recording", price = 2499.99),
//        ProductModel(name = "Fitbit Charge 5", description = "Fitness tracker with built-in GPS and heart rate monitor", price = 179.95),
//        ProductModel(name = "Philips Sonicare DiamondClean", description = "Electric toothbrush with multiple cleaning modes", price = 229.99),
//        ProductModel(name = "Dell XPS 13", description = "Compact laptop with Intel Core i7 processor and 4K display", price = 1399.99),
//        ProductModel(name = "HP Envy 6055e Printer", description = "Wireless inkjet printer with mobile printing features", price = 129.99),
//        ProductModel(name = "JBL Flip 5", description = "Portable Bluetooth speaker with deep bass and waterproof design", price = 119.95),
//        ProductModel(name = "Oculus Quest 2", description = "Virtual reality headset for immersive gaming experiences", price = 299.99),
//        ProductModel(name = "Samsung 27-inch Curved Monitor", description = "Full HD curved monitor with 75Hz refresh rate", price = 179.99),
//        ProductModel(name = "Instant Pot Duo 7-in-1", description = "Electric pressure cooker with multiple cooking modes", price = 89.99),
//        ProductModel(name = "Ninja Foodi Grill", description = "Indoor grill and air fryer with cyclonic grilling technology", price = 229.99)
//    )
//
//    val firestore = FirebaseFirestore.getInstance()
//
//    suspend fun addProductsToDatabase(products: List<ProductModel>) {
//        val productCollection = firestore.collection("products")
//
//        products.forEach { product ->
//            try {
//                // Add product to Firestore and automatically generate an ID
//                val productRef = productCollection.add(product).await()
//
//                // Optionally, you can store the Firestore document ID if needed elsewhere
//                Log.d("CartRepository", "Product added with Firestore ID: ${productRef.id}")
//            } catch (e: Exception) {
//                Log.e("CartRepository", "Error adding product: ${e.message}", e)
//            }
//        }
//    }

    init {
        fetchProducts()

//        viewModelScope.launch {
//            addProductsToDatabase(productsList)
//        }
    }

}