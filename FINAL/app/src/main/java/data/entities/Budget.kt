package com.example.gastos.data.entities

data class Budget(
    val id: String = java.util.UUID.randomUUID().toString(),
    val category: String,
    val monthlyLimit: Double,
    val spent: Double = 0.0  // Se calcula dinámicamente
)