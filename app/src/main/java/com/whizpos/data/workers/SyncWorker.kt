package com.whizpos.data.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.whizpos.data.local.database.SyncQueueDao
import com.whizpos.data.remote.SyncOperation
import com.whizpos.data.remote.WhizPosApiService
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val syncQueueDao: SyncQueueDao,
    private val apiService: WhizPosApiService,
    private val gson: Gson
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val pendingOperations = syncQueueDao.getPendingOperations()
        if (pendingOperations.isEmpty()) {
            return Result.success()
        }

        try {
            // In a real app, you'd properly deserialize based on 'type'
            val operations = pendingOperations.map { 
                gson.fromJson(it.data, SyncOperation::class.java) 
            }
            // apiService.pushData(operations) // Assuming the API key is handled by an interceptor

            // If successful, clear the queue
            pendingOperations.forEach { syncQueueDao.deleteOperation(it.id) }

            return Result.success()
        } catch (e: Exception) {
            // Network error or other issue, retry later
            return Result.retry()
        }
    }
}