package com.whizpos.ui.screens.pos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whizpos.domain.model.CartItem
import com.whizpos.domain.model.Product
import com.whizpos.domain.model.User
import com.whizpos.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class PosViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    // --- MOCK DATA --- //
    private val _currentUser = MutableStateFlow<User?>(User(1, "Admin User", "1234", "Admin", null))
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()
    // --- END MOCK DATA --- //

    val products: StateFlow<List<Product>> = productRepository.getProducts()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _cart = MutableStateFlow<List<CartItem>>(emptyList())
    val cart: StateFlow<List<CartItem>> = _cart.asStateFlow()

    fun addToCart(product: Product) {
        val existingItem = _cart.value.find { it.productId == product.id }
        if (existingItem != null) {
            _cart.update { cart ->
                cart.map {
                    if (it.productId == product.id) {
                        it.copy(quantity = it.quantity + 1)
                    } else {
                        it
                    }
                }
            }
        } else {
            _cart.update { cart ->
                cart + CartItem(product.id, 1, product.price)
            }
        }
    }
}