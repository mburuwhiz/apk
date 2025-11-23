package com.whizpos.data.repository

import com.google.gson.Gson
import com.whizpos.data.local.database.ExpenseDao
import com.whizpos.data.local.database.SyncQueueDao
import com.whizpos.data.local.entity.ExpenseEntity
import com.whizpos.data.local.entity.SyncQueueEntity
import com.whizpos.domain.model.Expense
import com.whizpos.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExpenseRepositoryImpl @Inject constructor(
    private val expenseDao: ExpenseDao,
    private val syncQueueDao: SyncQueueDao,
    private val gson: Gson
) : ExpenseRepository {

    override fun getExpenses(): Flow<List<Expense>> {
        return expenseDao.getExpenses().map { list ->
            list.map { it.toExpense() }
        }
    }

    override suspend fun addExpense(expense: Expense) {
        expenseDao.insertExpense(expense.toExpenseEntity())

        val operation = SyncQueueEntity(
            type = "add-expense",
            data = gson.toJson(expense)
        )
        syncQueueDao.enqueueOperation(operation)
    }

    // ... (rest of the file is unchanged)
}