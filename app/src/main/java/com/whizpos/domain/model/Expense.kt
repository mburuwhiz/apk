package com.whizpos.domain.model

import java.math.BigDecimal
import java.util.Date

data class Expense(
    val id: String,
    val amount: BigDecimal,
    val description: String,
    val category: String,
    val createdAt: Date
)