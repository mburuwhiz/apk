package com.whizpos.ui.screens.sync

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class SyncMonitorViewModel @Inject constructor(
    // TODO: Inject a SyncManager or Repository to get real data
) : ViewModel() {

    // --- MOCK DATA --- //
    private val _pendingItemCount = MutableStateFlow(0) // Mocked
    val pendingItemCount: StateFlow<Int> = _pendingItemCount

    private val _lastSyncTimestamp = MutableStateFlow<Date?>(null) // Mocked
    val lastSyncTimestamp: StateFlow<Date?> = _lastSyncTimestamp

    private val _syncLogs = MutableStateFlow(listOf("App Initialized")) // Mocked
    val syncLogs: StateFlow<List<String>> = _syncLogs
    // --- END MOCK DATA --- //

    fun onForcePush() {
        // TODO: Trigger a sync operation
        _syncLogs.value = _syncLogs.value + "Force push triggered."
        // Simulate success
        _pendingItemCount.value = 0
        _lastSyncTimestamp.value = Date()
        _syncLogs.value = _syncLogs.value + "Sync successful."
    }
}
