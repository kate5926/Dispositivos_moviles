package com.example.gastos.data.entities

data class Budget(
    val category: String,
    val spent: Double,
    val monthlyLimit: Double
)