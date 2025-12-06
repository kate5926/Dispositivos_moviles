package com.tupaquete.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "budgets")
data class Budget(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val category: String,
    val limitAmount: Double,
    var spentAmount: Double = 0.0,
    val month: Int, // 1-12
    val year: Int,
    val color: String = "#7C3AED",
    val createdAt: Date = Date()
) {
    // Métodos de ayuda
    fun getPercentageUsed(): Int {
        return if (limitAmount > 0) {
            ((spentAmount / limitAmount) * 100).toInt().coerceAtMost(100)
        } else {
            0
        }
    }

    fun getRemaining(): Double = limitAmount - spentAmount

    fun isOverLimit(): Boolean = spentAmount > limitAmount

    fun isWarning(): Boolean {
        val percentage = getPercentageUsed()
        return percentage in 80..99
    }

    fun getStatusColor(): String {
        return when {
            isOverLimit() -> "#EF4444"  // Rojo
            isWarning() -> "#F59E0B"    // Ámbar
            else -> color               // Color original
        }
    }

    fun getStatusText(): String {
        val remaining = getRemaining()
        return when {
            isOverLimit() -> "Excedido por $${String.format("%.2f", -remaining)}"
            isWarning() -> "Quedan $${String.format("%.2f", remaining)}"
            else -> "Quedan $${String.format("%.2f", remaining)}"
        }
    }
}