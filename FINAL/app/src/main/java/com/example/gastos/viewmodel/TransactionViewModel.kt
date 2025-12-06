package com.example.gastos.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.gastos.data.entities.Transaction
import com.example.gastos.data.managers.TransactionManager
import kotlinx.coroutines.launch
import java.util.Date

class TransactionViewModel(application: Application) : AndroidViewModel(application) {

    private val transactionManager = TransactionManager(application)

    // Propiedades para guardar
    private val _saveSuccess = MutableLiveData<Boolean>()
    val saveSuccess: LiveData<Boolean> = _saveSuccess

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    // NUEVA: Propiedad para la lista de transacciones
    private val _transactions = MutableLiveData<List<Transaction>>()
    val transactions: LiveData<List<Transaction>> get() = _transactions

    // Inicializar: cargar transacciones al crear el ViewModel
    init {
        loadAllTransactions()
    }

    // Función para cargar todas las transacciones
    fun loadAllTransactions() {
        viewModelScope.launch {
            try {
                val allTransactions = transactionManager.getAllTransactions()
                _transactions.value = allTransactions
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar transacciones: ${e.message}"
                _transactions.value = emptyList()
            }
        }
    }

    // Función para guardar transacción (actualizada)
    fun saveTransaction(
        amount: Double,
        category: String,
        date: Date,
        description: String,
        isIncome: Boolean,
        note: String = ""
    ) {
        viewModelScope.launch {
            try {
                // Obtener lista actual
                val currentTransactions = transactionManager.getAllTransactions().toMutableList()

                // Crear nueva transacción con ID único
                val newTransaction = Transaction(
                    id = System.currentTimeMillis(), // Usar timestamp como ID único
                    description = description,
                    amount = amount,
                    category = category,
                    isIncome = isIncome,
                    date = date,
                    note = note
                )

                // Agregar a la lista
                currentTransactions.add(newTransaction)

                // Guardar usando TransactionManager
                transactionManager.saveAllTransactions(currentTransactions)

                _saveSuccess.value = true
                _errorMessage.value = ""

                // Actualizar la lista observable
                _transactions.value = currentTransactions

            } catch (e: Exception) {
                _saveSuccess.value = false
                _errorMessage.value = "Error al guardar: ${e.message}"
            }
        }
    }

    // Función para eliminar transacción (opcional)
    fun deleteTransaction(transactionId: Long) {
        viewModelScope.launch {
            try {
                val currentTransactions = transactionManager.getAllTransactions()
                    .filter { it.id != transactionId }

                transactionManager.saveAllTransactions(currentTransactions)
                _transactions.value = currentTransactions
            } catch (e: Exception) {
                _errorMessage.value = "Error al eliminar: ${e.message}"
            }
        }
    }
}