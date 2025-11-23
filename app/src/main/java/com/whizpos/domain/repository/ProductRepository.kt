package com.whizpos.domain.repository

import com.whizpos.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProducts(): Flow<List<Product>>
    suspend fun syncProducts()
}