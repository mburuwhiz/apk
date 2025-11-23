package com.whizpos.data.repository

import com.whizpos.data.local.database.ProductDao
import com.whizpos.data.managers.SettingsManager
import com.whizpos.data.remote.WhizPosApiService
import com.whizpos.domain.model.Product
import com.whizpos.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val apiService: WhizPosApiService,
    private val productDao: ProductDao,
    private val settingsManager: SettingsManager
) : ProductRepository {

    override fun getProducts(): Flow<List<Product>> {
        return productDao.getProducts().map { entities ->
            entities.map { it.toProduct() }
        }
    }

    override suspend fun syncProducts() {
        val apiKey = settingsManager.connectionDetails.first().apiKey ?: return
        val remoteProducts = apiService.getSyncData(apiKey).products
        productDao.clearProducts()
        productDao.insertProducts(remoteProducts.map { it.toProductEntity() })
    }
}

private fun com.whizpos.data.local.entity.ProductEntity.toProduct(): Product {
    return Product(id, name, price, stock, imageUrl)
}