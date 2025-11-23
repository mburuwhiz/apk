package com.whizpos.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.whizpos.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarWithMenu(
    title: String,
    navController: NavController,
    userRole: String? // "Admin" or "Cashier"
) {
    var menuExpanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text(title) },
        actions = {
            IconButton(onClick = { menuExpanded = true }) {
                Icon(Icons.Default.Menu, contentDescription = "Menu")
            }
            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Transactions") },
                    onClick = { 
                        navController.navigate(Screen.Transactions.route)
                        menuExpanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Expenses") },
                    onClick = { 
                        navController.navigate(Screen.Expenses.route)
                        menuExpanded = false 
                    }
                )
                DropdownMenuItem(
                    text = { Text("Credit Customers") },
                    onClick = { 
                        navController.navigate(Screen.CreditCustomers.route)
                        menuExpanded = false
                    }
                )
                Divider()
                DropdownMenuItem(
                    text = { Text("Closing Summary") },
                    onClick = { 
                        navController.navigate(Screen.Closing.route)
                        menuExpanded = false
                    }
                )

                if (userRole == "Admin") {
                    Divider()
                    DropdownMenuItem(
                        text = { Text("Sync Monitor") },
                        onClick = { 
                            navController.navigate(Screen.SyncMonitor.route)
                            menuExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Settings") },
                        onClick = { 
                            navController.navigate(Screen.Settings.route)
                            menuExpanded = false
                        }
                    )
                }
            }
        }
    )
}