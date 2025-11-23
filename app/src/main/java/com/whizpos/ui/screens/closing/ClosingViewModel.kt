package com.whizpos.ui.screens.closing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whizpos.domain.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class ClosingViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    private val _totalSales = MutableStateFlow(BigDecimal.ZERO)
    val totalSales: StateFlow<BigDecimal> = _totalSales

    private val _transactionsCount = MutableStateFlow(0)
    val transactionsCount: StateFlow<Int> = _transactionsCount

    init {
        viewModelScope.launch {
            transactionRepository.getTransactions().map { transactions ->
                // Ideally, filter for the current shift/day
                _totalSales.value = transactions.sumOf { it.total }
                _transactionsCount.value = transactions.size
            }.collect{}
        }
    }
}