package com.whizpos.ui.screens.login

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whizpos.R
import com.whizpos.domain.model.User
import com.whizpos.ui.components.PinPad
import com.whizpos.ui.components.UserAvatar

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit
) {
    val users by viewModel.users.collectAsState()
    var selectedUser by remember { mutableStateOf<User?>(null) }
    val loginState by viewModel.loginState.collectAsState()

    LaunchedEffect(loginState) {
        if (loginState is LoginState.Success) {
            onLoginSuccess()
            viewModel.resetLoginState() // Reset for next login attempt
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .blur(radius = 16.dp)
        )

        Crossfade(targetState = selectedUser) {
            if (it == null) {
                UserSelectionGrid(users = users) { user ->
                    selectedUser = user
                }
            } else {
                PinEntry(user = it, loginState = loginState) { pin ->
                    viewModel.validatePin(it, pin)
                }
            }
        }
    }
}

@Composable
private fun UserSelectionGrid(users: List<User>, onUserSelected: (User) -> Unit) {
    // ... (omitted for brevity, no changes)
}

@Composable
private fun PinEntry(user: User, loginState: LoginState, onPinEntered: (String) -> Unit) {
    val isError = loginState is LoginState.Failure
    // TODO: Add shake animation on error

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UserAvatar(user = user, onUserSelected = {}) 
        Spacer(modifier = Modifier.height(32.dp))
        PinPad(onPinEntered = onPinEntered)
        if (isError) {
            Text("Invalid PIN", color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 16.dp))
        }
    }
}