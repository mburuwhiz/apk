package com.whizpos.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whizpos.domain.model.User
import com.whizpos.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class LoginState {
    object Idle : LoginState()
    object Success : LoginState()
    object Failure : LoginState()
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    val users: StateFlow<List<User>> = userRepository.getUsers()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    fun validatePin(user: User, pin: String) {
        viewModelScope.launch {
            if (user.pin == pin) {
                // In a real app, you would store the current user session here
                _loginState.value = LoginState.Success
            } else {
                _loginState.value = LoginState.Failure
                // Reset after a short delay so the UI can show an error state
                kotlinx.coroutines.delay(1000)
                _loginState.value = LoginState.Idle
            }
        }
    }
    
    fun resetLoginState() {
        _loginState.value = LoginState.Idle
    }
}