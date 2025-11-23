package com.whizpos.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.KeyboardType
import com.whizpos.domain.model.CreditCustomer
import java.math.BigDecimal

@Composable
fun PayDebtDialog(
    customer: CreditCustomer,
    onDismissRequest: () -> Unit,
    onConfirm: (BigDecimal) -> Unit
) {
    var amount by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("Pay Debt for ${customer.name}") },
        text = {
            Column {
                Text("Current Balance: KES ${customer.balance}")
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Amount to Pay") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { 
                    val paymentAmount = amount.toBigDecimalOrNull() ?: BigDecimal.ZERO
                    onConfirm(paymentAmount) 
                }
            ) {
                Text("Confirm Payment")
            }
        },
        dismissButton = {
            Button(onClick = onDismissRequest) {
                Text("Cancel")
            }
        }
    )
}