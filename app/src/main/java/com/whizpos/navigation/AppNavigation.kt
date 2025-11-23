package com.whizpos.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.whizpos.ui.screens.checkout.CheckoutScreen
import com.whizpos.ui.screens.closing.ClosingScreen
import com.whizpos.ui.screens.connections.ConnectionScreen
import com.whizpos.ui.screens.credit.CreditCustomersScreen
import com.whizpos.ui.screens.expenses.ExpensesScreen
import com.whizpos.ui.screens.login.LoginScreen
import com.whizpos.ui.screens.pos.PosScreen
import com.whizpos.ui.screens.settings.SettingsScreen
import com.whizpos.ui.screens.sync.SyncMonitorScreen
import com.whizpos.ui.screens.transactions.TransactionsScreen

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Pos : Screen("pos")
    object Checkout : Screen("checkout")
    object Transactions : Screen("transactions")
    object Expenses : Screen("expenses")
    object CreditCustomers : Screen("credit_customers")
    object Closing : Screen("closing")
    object ConnectionSetup : Screen("connection_setup")
    object SyncMonitor : Screen("sync_monitor")
    object Settings : Screen("settings")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.ConnectionSetup.route) {
        composable(Screen.ConnectionSetup.route) {
            ConnectionScreen(onConnectionSuccess = {
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.ConnectionSetup.route) { inclusive = true }
                }
            })
        }
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Pos.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Pos.route) {
            PosScreen(navController = navController)
        }
        composable(Screen.Checkout.route) {
            CheckoutScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Screen.Transactions.route) {
            TransactionsScreen(navController = navController)
        }
        composable(Screen.Expenses.route) {
            ExpensesScreen(navController = navController)
        }
        composable(Screen.CreditCustomers.route) {
            CreditCustomersScreen(navController = navController)
        }
        composable(Screen.Closing.route) {
            ClosingScreen(navController = navController)
        }
        composable(Screen.SyncMonitor.route) {
            SyncMonitorScreen(navController = navController)
        }
        composable(Screen.Settings.route) {
            SettingsScreen(navController = navController)
        }
    }
}