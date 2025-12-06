//PRESUPUESTOS
package com.example.gastos.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gastos.data.entities.Budget
import com.example.gastos.data.managers.BudgetManager

class BudgetViewModel(application: Application) : AndroidViewModel(application) {

    private val budgetManager = BudgetManager(application)

    private val _budgets = MutableLiveData<List<Budget>>()
    val budgets: LiveData<List<Budget>> = _budgets

    init {
        loadBudgets()
    }

    private fun loadBudgets() {
        _budgets.value = budgetManager.getAllBudgets()
    }

    fun addBudget(budget: Budget) {
        budgetManager.saveBudget(budget)
        loadBudgets()
    }
}