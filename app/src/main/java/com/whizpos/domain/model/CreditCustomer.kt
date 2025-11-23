package com.whizpos.domain.model

import java.math.BigDecimal

data class CreditCustomer(
    val id: String,
    val name: String,
    val phone: String?,
    val balance: BigDecimal
)