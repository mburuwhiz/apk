package com.whizpos.data.remote

import com.whizpos.domain.model.Transaction

sealed class SyncOperation {
    data class NewTransaction(val data: Transaction) : SyncOperation() { 
        val type = "new-transaction"
    }
    // ... other operation types
}