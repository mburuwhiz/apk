package com.whizpos.ui.screens.credit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.whizpos.domain.model.CreditCustomer
import com.whizpos.ui.components.AddCustomerDialog
import com.whizpos.ui.components.CustomerListItem
import com.whizpos.ui.components.PayDebtDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreditCustomersScreen(
    viewModel: CreditCustomersViewModel = hiltViewModel(),
    navController: NavController
) {
    val customers by viewModel.creditCustomers.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var showAddDialog by remember { mutableStateOf(false) }
    var customerToPay by remember { mutableStateOf<CreditCustomer?>(null) }

    if (showAddDialog) {
        AddCustomerDialog(
            onDismissRequest = { showAddDialog = false },
            onConfirm = {
                viewModel.addCreditCustomer(it)
                showAddDialog = false
            }
        )
    }

    customerToPay?.let {
        PayDebtDialog(
            customer = it,
            onDismissRequest = { customerToPay = null },
            onConfirm = { amount ->
                viewModel.makePayment(it, amount)
                customerToPay = null
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Credit Customers") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Customer")
            }
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search Customers") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            LazyColumn {
                items(customers.filter { customer -> customer.name.contains(searchQuery, ignoreCase = true) }) { customer ->
                    CustomerListItem(customer = customer) {
                        customerToPay = customer
                    }
                }
            }
        }
    }
}