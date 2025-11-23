package com.whizpos.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.whizpos.data.local.entity.*

@Database(
    entities = [
        UserEntity::class,
        ProductEntity::class,
        TransactionEntity::class,
        CartItemEntity::class,
        CreditCustomerEntity::class,
        ExpenseEntity::class,
        SyncQueueEntity::class // Added new entity
    ],
    version = 2, // Incremented version
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class WhizPosDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun productDao(): ProductDao
    abstract fun transactionDao(): TransactionDao
    abstract fun creditCustomerDao(): CreditCustomerDao
    abstract fun expenseDao(): ExpenseDao
    abstract fun syncQueueDao(): SyncQueueDao // Added new DAO
}