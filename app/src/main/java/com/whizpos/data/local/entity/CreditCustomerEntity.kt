package com.whizpos.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(tableName = "credit_customers")
data class CreditCustomerEntity(
    @PrimaryKey val id: String,
    val name: String,
    val phone: String?,
    val balance: BigDecimal
)