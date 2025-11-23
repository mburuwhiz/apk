package com.whizpos.ui.screens.closing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClosingScreen(
    viewModel: ClosingViewModel = hiltViewModel(),
    navController: NavController
) {
    val totalSales by viewModel.totalSales.collectAsState()
    val transactionCount by viewModel.transactionsCount.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("End-of-Shift Summary") },
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
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Total Sales: KES $totalSales", style = MaterialTheme.typography.headlineMedium)
            Text("Total Transactions: $transactionCount", style = MaterialTheme.typography.headlineMedium)
            // TODO: Add button to print Z-report
        }
    }
}