package com.whizpos.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PinPad(
    onPinEntered: (String) -> Unit,
    onBiometricAuth: (() -> Unit)? = null // Optional biometric auth
) {
    var pin by remember { mutableStateOf("") }
    val buttons = (1..9).map { it.toString() } + listOf("", "0", "<-")

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Enter PIN", fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp))
        Text(pin, fontSize = 32.sp, modifier = Modifier.padding(bottom = 16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.width(240.dp)
        ) {
            items(buttons) { buttonText ->
                if (buttonText.isEmpty()) {
                    Spacer(modifier = Modifier.size(80.dp))
                } else {
                    PinButton(text = buttonText) {
                        when (it) {
                            "<-" -> pin = pin.dropLast(1)
                            else -> if (pin.length < 4) pin += it
                        }
                        if (pin.length == 4) {
                            onPinEntered(pin)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PinButton(text: String, onClick: (String) -> Unit) {
    Box(
        modifier = Modifier.padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = { onClick(text) },
            modifier = Modifier.size(72.dp),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.2f))
        ) {
            if (text == "<-") {
                Icon(Icons.Default.ArrowBack, contentDescription = "Backspace")
            } else {
                Text(text, fontSize = 28.sp, textAlign = TextAlign.Center)
            }
        }
    }
}