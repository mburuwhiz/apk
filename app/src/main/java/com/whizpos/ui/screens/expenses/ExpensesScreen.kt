package com.whizpos.ui.screens.expenses

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.whizpos.ui.components.AddExpenseDialog
import com.whizpos.ui.components.ExpenseListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesScreen(
    viewModel: ExpensesViewModel = hiltViewModel(),
    navController: NavController
) {
    val expenses by viewModel.expenses.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AddExpenseDialog(
            onDismissRequest = { showDialog = false },
            onConfirm = {
                viewModel.addExpense(it)
                showDialog = false
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Expenses") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Expense")
            }
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            if (expenses.isEmpty()) {
                item {
                    Text("No expenses logged.", modifier = Modifier.padding(16.dp))
                }
            } else {
                items(expenses) { expense ->
                    ExpenseListItem(expense = expense)
                }
            }
        }
    }
}