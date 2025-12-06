package com.example.gastos.data.entities

import java.util.Date

data class Transaction(
    val id: String = java.util.UUID.randomUUID().toString(),  // ID único
    val amount: Double,
    val category: String,
    val date: Date,
    val description: String,
    val isIncome: Boolean  // true = ingreso, false = gasto
)