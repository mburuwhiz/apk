package com.whizpos.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.whizpos.data.local.entity.CreditCustomerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CreditCustomerDao {
    @Query("SELECT * FROM credit_customers ORDER BY name ASC")
    fun getCreditCustomers(): Flow<List<CreditCustomerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCreditCustomer(customer: CreditCustomerEntity)

    @Update
    suspend fun updateCreditCustomer(customer: CreditCustomerEntity)
}