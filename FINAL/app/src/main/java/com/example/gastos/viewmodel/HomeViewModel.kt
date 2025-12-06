package com.example.gastos.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gastos.data.managers.TransactionManager

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val transactionManager = TransactionManager(application)

    private val _balance = MutableLiveData<Double>()
    val balance: LiveData<Double> = _balance

    private val _income = MutableLiveData<Double>()
    val income: LiveData<Double> = _income

    private val _expenses = MutableLiveData<Double>()
    val expenses: LiveData<Double> = _expenses

    init {
        loadData()
    }

    private fun loadData() {
        _income.value = transactionManager.getTotalIncome()
        _expenses.value = transactionManager.getTotalExpenses()
        _balance.value = transactionManager.getBalance()
    }
}