package com.whizpos.ui.screens.credit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whizpos.domain.model.CreditCustomer
import com.whizpos.domain.repository.CreditCustomerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class CreditCustomersViewModel @Inject constructor(
    private val creditCustomerRepository: CreditCustomerRepository
) : ViewModel() {

    val creditCustomers: StateFlow<List<CreditCustomer>> = creditCustomerRepository.getCreditCustomers()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addCreditCustomer(customer: CreditCustomer) {
        viewModelScope.launch {
            creditCustomerRepository.addCreditCustomer(customer)
        }
    }

    fun makePayment(customer: CreditCustomer, amount: BigDecimal) {
        viewModelScope.launch {
            val updatedCustomer = customer.copy(balance = customer.balance.add(amount))
            creditCustomerRepository.updateCreditCustomer(updatedCustomer)
        }
    }
}