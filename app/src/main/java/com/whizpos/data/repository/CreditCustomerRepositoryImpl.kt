package com.whizpos.data.repository

import com.google.gson.Gson
import com.whizpos.data.local.database.CreditCustomerDao
import com.whizpos.data.local.database.SyncQueueDao
import com.whizpos.data.local.entity.CreditCustomerEntity
import com.whizpos.data.local.entity.SyncQueueEntity
import com.whizpos.domain.model.CreditCustomer
import com.whizpos.domain.repository.CreditCustomerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CreditCustomerRepositoryImpl @Inject constructor(
    private val creditCustomerDao: CreditCustomerDao,
    private val syncQueueDao: SyncQueueDao,
    private val gson: Gson
) : CreditCustomerRepository {

    override fun getCreditCustomers(): Flow<List<CreditCustomer>> {
        return creditCustomerDao.getCreditCustomers().map { list ->
            list.map { it.toCreditCustomer() }
        }
    }

    override suspend fun addCreditCustomer(customer: CreditCustomer) {
        creditCustomerDao.insertCreditCustomer(customer.toCreditCustomerEntity())

        val operation = SyncQueueEntity(
            type = "add-credit-customer",
            data = gson.toJson(customer)
        )
        syncQueueDao.enqueueOperation(operation)
    }

    override suspend fun updateCreditCustomer(customer: CreditCustomer) {
        creditCustomerDao.updateCreditCustomer(customer.toCreditCustomerEntity())

        val operation = SyncQueueEntity(
            type = "update-credit-customer",
            data = gson.toJson(customer)
        )
        syncQueueDao.enqueueOperation(operation)
    }

    // ... (rest of the file is unchanged)
}