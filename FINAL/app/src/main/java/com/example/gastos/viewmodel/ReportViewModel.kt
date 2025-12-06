package com.example.gastos.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gastos.data.managers.TransactionManager
import java.util.Calendar

class ReportViewModel(application: Application) : AndroidViewModel(application) {

    private val transactionManager = TransactionManager(application)

    // Mes y año seleccionados
    private val _selectedMonth = MutableLiveData(Calendar.getInstance().get(Calendar.MONTH) + 1)
    val selectedMonth: LiveData<Int> = _selectedMonth

    private val _selectedYear = MutableLiveData(Calendar.getInstance().get(Calendar.YEAR))
    val selectedYear: LiveData<Int> = _selectedYear

    // Estadísticas
    private val _totalIncome = MutableLiveData<Double>()
    val totalIncome: LiveData<Double> = _totalIncome

    private val _totalExpense = MutableLiveData<Double>()
    val totalExpense: LiveData<Double> = _totalExpense

    private val _netBalance = MutableLiveData<Double>()
    val netBalance: LiveData<Double> = _netBalance

    private val _dailyAverage = MutableLiveData<Double>()
    val dailyAverage: LiveData<Double> = _dailyAverage

    private val _incomePercent = MutableLiveData<Int>()
    val incomePercent: LiveData<Int> = _incomePercent

    private val _expensePercent = MutableLiveData<Int>()
    val expensePercent: LiveData<Int> = _expensePercent

    init {
        updateReport()
    }

    fun setMonth(month: Int) {
        _selectedMonth.value = month
        updateReport()
    }

    fun setYear(year: Int) {
        _selectedYear.value = year
        updateReport()
    }

    fun nextMonth() {
        val currentMonth = _selectedMonth.value ?: 1
        val currentYear = _selectedYear.value ?: Calendar.getInstance().get(Calendar.YEAR)

        var newMonth = currentMonth + 1
        var newYear = currentYear

        if (newMonth > 12) {
            newMonth = 1
            newYear++
        }

        _selectedMonth.value = newMonth
        _selectedYear.value = newYear
        updateReport()
    }

    fun prevMonth() {
        val currentMonth = _selectedMonth.value ?: 1
        val currentYear = _selectedYear.value ?: Calendar.getInstance().get(Calendar.YEAR)

        var newMonth = currentMonth - 1
        var newYear = currentYear

        if (newMonth < 1) {
            newMonth = 12
            newYear--
        }

        _selectedMonth.value = newMonth
        _selectedYear.value = newYear
        updateReport()
    }

    private fun updateReport() {
        val month = _selectedMonth.value ?: (Calendar.getInstance().get(Calendar.MONTH) + 1)
        val year = _selectedYear.value ?: Calendar.getInstance().get(Calendar.YEAR)

        // Filtra transacciones por mes/año
        val transactions = transactionManager.getTransactionsByMonth(year, month)
        val income = transactions.filter { it.isIncome }.sumOf { it.amount }
        val expense = transactions.filter { !it.isIncome }.sumOf { it.amount }
        val balance = income - expense

        _totalIncome.value = income
        _totalExpense.value = expense
        _netBalance.value = balance

        // Porcentajes para gráfico
        val total = income + expense
        _incomePercent.value = if (total > 0) ((income / total) * 100).toInt() else 0
        _expensePercent.value = if (total > 0) ((expense / total) * 100).toInt() else 0

        // Promedio diario (balance / días del mes)
        val daysInMonth = Calendar.getInstance().apply {
            set(year, month - 1, 1)
        }.getActualMaximum(Calendar.DAY_OF_MONTH)

        _dailyAverage.value = if (daysInMonth > 0) balance / daysInMonth else 0.0
    }
}