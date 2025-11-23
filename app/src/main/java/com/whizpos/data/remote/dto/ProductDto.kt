package com.whizpos.data.remote.dto

import com.whizpos.data.local.entity.ProductEntity
import java.math.BigDecimal

data class ProductDto(
    val id: String,
    val name: String,
    val price: BigDecimal,
    val stock: Int,
    val imageUrl: String?
) {
    fun toProductEntity(): ProductEntity {
        return ProductEntity(
            id = id,
            name = name,
            price = price,
            stock = stock,
            imageUrl = imageUrl
        )
    }
}