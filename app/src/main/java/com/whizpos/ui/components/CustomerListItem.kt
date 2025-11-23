package com.whizpos.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.whizpos.domain.model.CreditCustomer
import java.math.BigDecimal

@Composable
fun CustomerListItem(
    customer: CreditCustomer,
    onPayDebtClicked: (CreditCustomer) -> Unit
) {
    val balanceColor = if (customer.balance < BigDecimal.ZERO) MaterialTheme.colorScheme.error else Color.Green

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = customer.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                customer.phone?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "KES ${customer.balance}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = balanceColor
                )
                if (customer.balance < BigDecimal.ZERO) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Button(onClick = { onPayDebtClicked(customer) }) {
                        Text("Pay Debt")
                    }
                }
            }
        }
    }
}