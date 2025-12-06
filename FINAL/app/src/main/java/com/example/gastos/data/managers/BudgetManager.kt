package com.example.gastos.data.managers

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.gastos.data.entities.Budget

class BudgetManager(private val context: Context) {

    private val prefs = context.getSharedPreferences("gastos_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveBudget(budget: Budget) {
        val list = getAllBudgets().toMutableList()
        list.add(budget)
        val json = gson.toJson(list)
        prefs.edit().putString("budgets", json).apply()
    }

    fun getAllBudgets(): List<Budget> {
        val json = prefs.getString("budgets", "[]")
        val type = object : TypeToken<List<Budget>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }

    fun updateSpent(category: String, spent: Double) {
        val list = getAllBudgets().toMutableList()
        val index = list.indexOfFirst { it.category == category }
        if (index != -1) {
            list[index] = list[index].copy(spent = spent)
            val json = gson.toJson(list)
            prefs.edit().putString("budgets", json).apply()
        }
    }
}