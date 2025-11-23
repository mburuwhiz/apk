package com.whizpos.domain.model

import java.math.BigDecimal
import java.util.Date

data class Transaction(
    val id: String,
    val items: List<CartItem>,
    val total: BigDecimal,
    val paymentMethod: String, // "CASH", "M-PESA", "CREDIT"
    val customerId: String?,
    val cashierId: Int,
    val createdAt: Date
)

data class CartItem(
    val productId: String,
    val quantity: Int,
    val price: BigDecimal
)