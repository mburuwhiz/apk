package com.whizpos.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.whizpos.domain.model.CreditCustomer
import java.math.BigDecimal
import java.util.UUID

@Composable
fun AddCustomerDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (CreditCustomer) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("Add New Credit Customer") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Customer Name") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Phone Number (Optional)") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val newCustomer = CreditCustomer(
                        id = UUID.randomUUID().toString(),
                        name = name,
                        phone = phone,
                        balance = BigDecimal.ZERO
                    )
                    onConfirm(newCustomer)
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            Button(onClick = onDismissRequest) {
                Text("Cancel")
            }
        }
    )
}