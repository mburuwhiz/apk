package com.whizpos.data.repository

import com.google.gson.Gson
import com.whizpos.data.local.database.SyncQueueDao
import com.whizpos.data.local.database.TransactionDao
import com.whizpos.data.local.entity.CartItemEntity
import com.whizpos.data.local.entity.SyncQueueEntity
import com.whizpos.data.local.entity.TransactionEntity
import com.whizpos.data.local.entity.TransactionWithItems
import com.whizpos.domain.model.CartItem
import com.whizpos.domain.model.Transaction
import com.whizpos.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val transactionDao: TransactionDao,
    private val syncQueueDao: SyncQueueDao,
    private val gson: Gson
) : TransactionRepository {

    override fun getTransactions(): Flow<List<Transaction>> {
        return transactionDao.getTransactions().map { list ->
            list.map { it.toTransaction() }
        }
    }

    override suspend fun addTransaction(transaction: Transaction) {
        // Save to local database first
        transactionDao.insertTransaction(transaction.toTransactionEntity())
        transactionDao.insertCartItems(transaction.items.map { it.toCartItemEntity(transaction.id) })

        // Enqueue for network push
        val operation = SyncQueueEntity(
            type = "new-transaction",
            data = gson.toJson(transaction)
        )
        syncQueueDao.enqueueOperation(operation)
    }

    // ... (rest of the file is unchanged)
}