package com.whizpos.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.whizpos.domain.model.CartItem
import java.math.BigDecimal
import java.util.Date

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey val id: String,
    val total: BigDecimal,
    val paymentMethod: String,
    val customerId: String?,
    val cashierId: Int,
    val createdAt: Date
)

data class TransactionWithItems(
    @Embedded val transaction: TransactionEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "transactionId"
    )
    val items: List<CartItemEntity>
)

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val transactionId: String,
    val productId: String,
    val quantity: Int,
    val price: BigDecimal
)