package com.whizpos.domain.repository

import com.whizpos.domain.model.CreditCustomer
import kotlinx.coroutines.flow.Flow

interface CreditCustomerRepository {
    fun getCreditCustomers(): Flow<List<CreditCustomer>>
    suspend fun addCreditCustomer(customer: CreditCustomer)
    suspend fun updateCreditCustomer(customer: CreditCustomer)
}