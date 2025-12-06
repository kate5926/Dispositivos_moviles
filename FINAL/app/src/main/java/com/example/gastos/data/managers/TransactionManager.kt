package com.example.gastos.data.managers

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.gastos.data.entities.Transaction
import java.util.Calendar

class TransactionManager(private val context: Context) {

    private val prefs = context.getSharedPreferences("gastos_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    // En TransactionManager.kt
    fun saveAllTransactions(transactions: List<Transaction>) {
        val json = gson.toJson(transactions)
        prefs.edit().putString("transactions", json).apply()
    }

    fun getAllTransactions(): List<Transaction> {
        val json = prefs.getString("transactions", "[]")
        val type = object : TypeToken<List<Transaction>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }

    fun getTransactionsByMonth(year: Int, month: Int): List<Transaction> {
        return getAllTransactions().filter { transaction ->
            val cal = Calendar.getInstance().apply { time = transaction.date }
            cal.get(Calendar.YEAR) == year && (cal.get(Calendar.MONTH) + 1) == month
        }
    }

    fun getTotalIncome(): Double = getAllTransactions()
        .filter { it.isIncome }
        .sumOf { it.amount }

    fun getTotalExpenses(): Double = getAllTransactions()
        .filter { !it.isIncome }
        .sumOf { it.amount }

    fun getBalance(): Double = getTotalIncome() - getTotalExpenses()
}