package com.whizpos.ui.screens.sync

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SyncMonitorScreen(
    viewModel: SyncMonitorViewModel = hiltViewModel(),
    navController: NavController
) {
    val pendingItems by viewModel.pendingItemCount.collectAsState()
    val lastSync by viewModel.lastSyncTimestamp.collectAsState()
    val logs by viewModel.syncLogs.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sync Monitor") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text("Pending Items: $pendingItems", style = MaterialTheme.typography.titleMedium)
            val lastSyncText = lastSync?.let { ts ->
                SimpleDateFormat("hh:mm:ss a", Locale.getDefault()).format(ts)
            } ?: "Never"
            Text("Last Sync: $lastSyncText", style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { viewModel.onForcePush() }, modifier = Modifier.fillMaxWidth()) {
                Text("Force Push")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Logs:", style = MaterialTheme.typography.titleMedium)
            Divider()

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(logs.reversed()) { log ->
                    Text(log, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}