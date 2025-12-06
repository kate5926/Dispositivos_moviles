package com.example.gastos.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gastos.data.entities.Transaction
import com.example.gastos.data.managers.TransactionManager

class TransactionViewModel(application: Application) : AndroidViewModel(application) {
    // Ya tienes esto, pero confirma
    private val _transactions = MutableLiveData<List<Transaction>>()
    val transactions: LiveData<List<Transaction>> = _transactions
// En loadAllData(), agrega:
    _transactions.postValue(transactionManager.getAllTransactions())

    private val transactionManager = TransactionManager(application)

    private val _saveSuccess = MutableLiveData<Boolean>()
    val saveSuccess: LiveData<Boolean> = _saveSuccess

    fun saveTransaction(amount: Double, category: String, date: java.util.Date, description: String, isIncome: Boolean) {
        val transaction = Transaction(amount = amount, category = category, date = date, description = description, isIncome = isIncome)
        transactionManager.saveTransaction(transaction)
        _saveSuccess.value = true
    }
}