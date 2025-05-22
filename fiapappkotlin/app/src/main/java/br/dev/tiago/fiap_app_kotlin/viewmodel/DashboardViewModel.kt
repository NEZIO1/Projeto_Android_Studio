package br.dev.tiago.fiap_app_kotlin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.dev.tiago.fiap_app_kotlin.repository.TransactionRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class DashboardViewModel(private val repository: TransactionRepository) : ViewModel() {

    private val _selectedMonth = MutableStateFlow(getCurrentMonth())
    val selectedMonth: StateFlow<String> = _selectedMonth

    private val _selectedYear = MutableStateFlow(getCurrentYear())
    val selectedYear: StateFlow<String> = _selectedYear

    private val _goalAmount = MutableStateFlow(0.0)
    val goalAmount: StateFlow<Double> = _goalAmount

    val totalInvestments = _selectedMonth.combine(_selectedYear) { month, year ->
        repository.getTotalInvestments(month, year)
    }.flatMapLatest { it }.stateIn(viewModelScope, SharingStarted.Lazily, 0.0)

    val totalRevenue = _selectedMonth.combine(_selectedYear) { month, year ->
        repository.getTotalRevenue(month, year)
    }.flatMapLatest { it }.stateIn(viewModelScope, SharingStarted.Lazily, 0.0)

    val totalExpenses = _selectedMonth.combine(_selectedYear) { month, year ->
        repository.getTotalExpenses(month, year)
    }.flatMapLatest { it }.stateIn(viewModelScope, SharingStarted.Lazily, 0.0)

    fun setGoalAmount(amount: Double) {
        _goalAmount.value = amount
    }

    fun setSelectedMonth(month: String) {
        _selectedMonth.value = month
    }

    fun setSelectedYear(year: String) {
        _selectedYear.value = year
    }

    private fun getCurrentMonth(): String {
        return SimpleDateFormat("MM", Locale.getDefault()).format(Date())
    }

    private fun getCurrentYear(): String {
        return SimpleDateFormat("yyyy", Locale.getDefault()).format(Date())
    }
}
