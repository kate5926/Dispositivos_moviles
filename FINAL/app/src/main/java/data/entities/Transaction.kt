package com.example.gastos.data.entities

import java.util.Date

data class Transaction(
    val id: Long,  // Asegúrate de tener este campo
    val description: String,
    val amount: Double,
    val category: String,
    val isIncome: Boolean,
    val date: Date,
    val note: String = ""
)