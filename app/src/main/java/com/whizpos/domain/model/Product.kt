package com.whizpos.domain.model

import java.math.BigDecimal

data class Product(
    val id: String,
    val name: String,
    val price: BigDecimal,
    val stock: Int,
    val imageUrl: String?
)