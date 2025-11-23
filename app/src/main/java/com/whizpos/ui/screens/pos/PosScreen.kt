package com.whizpos.ui.screens.pos

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.whizpos.navigation.Screen
import com.whizpos.ui.components.CategoryPills
import com.whizpos.ui.components.FloatingCartBar
import com.whizpos.ui.components.ProductCard
import com.whizpos.ui.components.TopAppBarWithMenu

@Composable
fun PosScreen(
    viewModel: PosViewModel = hiltViewModel(),
    navController: NavController
) {
    val products by viewModel.products.collectAsState()
    val cart by viewModel.cart.collectAsState()
    val currentUser by viewModel.currentUser.collectAsState()

    val categories = products.map { it.name.substring(0, 1) }.distinct() // Dummy categories
    val selectedCategory = remember { mutableStateOf("All") }

    Scaffold(
        topBar = { 
            TopAppBarWithMenu(
                title = "Whiz POS", 
                navController = navController, 
                userRole = currentUser?.role
            ) 
        },
    ) {
        Box(modifier = Modifier.fillMaxSize().padding(it)) {
            Column(modifier = Modifier.fillMaxSize()) {
                CategoryPills(
                    categories = listOf("All") + categories,
                    selectedCategory = selectedCategory.value,
                    onCategorySelected = { selectedCategory.value = it }
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = 80.dp) // Space for the floating bar
                ) {
                    items(products.filter { selectedCategory.value == "All" || it.name.startsWith(selectedCategory.value) }) {
                        ProductCard(product = it, onProductClick = { viewModel.addToCart(it) })
                    }
                }
            }

            val totalItems = cart.sumOf { it.quantity }
            val totalPrice = cart.sumOf { it.price.multiply(it.quantity.toBigDecimal()) }

            if (totalItems > 0) {
                Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                    FloatingCartBar(
                        itemCount = totalItems,
                        totalPrice = totalPrice,
                        onBarClick = { navController.navigate(Screen.Checkout.route) }
                    )
                }
            }
        }
    }
}