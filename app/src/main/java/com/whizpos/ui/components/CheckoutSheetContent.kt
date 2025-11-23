package com.whizpos.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CheckoutSheetContent(
    onPaymentMethodSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Select Payment Method", 
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            PaymentMethodCard(method = "CASH", description = "For cash payments") { onPaymentMethodSelected("CASH") }
            PaymentMethodCard(method = "M-PESA", description = "For mobile money") { onPaymentMethodSelected("M-PESA") }
            PaymentMethodCard(method = "CREDIT", description = "Charge to customer account") { onPaymentMethodSelected("CREDIT") }
        }
    }
}

@Composable
private fun PaymentMethodCard(method: String, description: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .size(width = 110.dp, height = 100.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(method, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(description, fontSize = 12.sp, color = Color.Gray, textAlign = TextAlign.Center)
        }
    }
}