package com.whizpos.ui.screens.connections

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whizpos.data.managers.SettingsManager
import com.whizpos.domain.repository.ProductRepository
import com.whizpos.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConnectionViewModel @Inject constructor(
    private val settingsManager: SettingsManager,
    private val userRepository: UserRepository,
    private val productRepository: ProductRepository
) : ViewModel() {

    fun onConnectClicked(serverUrl: String, apiKey: String, onConnectionSuccess: () -> Unit) {
        viewModelScope.launch {
            settingsManager.saveConnectionDetails(serverUrl, apiKey)
            // Trigger initial sync
            userRepository.syncUsers()
            productRepository.syncProducts()
            onConnectionSuccess()
        }
    }
}