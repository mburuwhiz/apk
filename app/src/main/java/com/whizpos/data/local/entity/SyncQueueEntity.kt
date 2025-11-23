package com.whizpos.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "sync_queue")
data class SyncQueueEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: String, // e.g., "new-transaction", "add-expense"
    val data: String, // JSON representation of the operation data
    val createdAt: Date = Date()
)