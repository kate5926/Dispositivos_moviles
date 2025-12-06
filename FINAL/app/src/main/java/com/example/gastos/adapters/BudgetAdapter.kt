package com.example.gastos.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.gastos.R
import com.example.gastos.data.entities.Budget
import com.example.gastos.databinding.ItemBudgetBinding

class BudgetAdapter : RecyclerView.Adapter<BudgetAdapter.BudgetViewHolder>() {

    private var budgets: List<Budget> = emptyList()

    // Esta función SÍ se usa en BudgetFragment.kt
    fun submitList(list: List<Budget>) {
        budgets = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudgetViewHolder {
        val binding = ItemBudgetBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BudgetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BudgetViewHolder, position: Int) {
        holder.bind(budgets[position])
    }

    override fun getItemCount(): Int = budgets.size

    class BudgetViewHolder(private val binding: ItemBudgetBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(budget: Budget) {
            // Establecer textos
            binding.tvCategory.text = budget.category
            binding.tvSpent.text = String.format("$%.2f", budget.spent)
            binding.tvLimit.text = String.format("de $%.2f", budget.monthlyLimit)

            // Calcular porcentaje
            val percent = if (budget.monthlyLimit > 0) {
                ((budget.spent / budget.monthlyLimit) * 100).toInt()
            } else 0

            binding.tvPercent.text = "$percent%"
            binding.tvRemaining.text = String.format(
                "Quedan $%.2f disponibles",
                budget.monthlyLimit - budget.spent
            )

            // Configurar barra de progreso DESPUÉS de que la vista se mida
            binding.root.post {
                val maxWidth = binding.progressBackground.width
                val progressWidth = (maxWidth * percent / 100).coerceAtMost(maxWidth)

                // Actualizar ancho de la barra
                binding.progressFill.layoutParams.width = progressWidth
                binding.progressFill.requestLayout()

                // Color dinámico según porcentaje
                val colorRes = when {
                    percent >= 100 -> R.color.red_500
                    percent >= 90 -> R.color.amber_500
                    else -> R.color.emerald_400
                }

                val color = ContextCompat.getColor(itemView.context, colorRes)
                binding.progressFill.setBackgroundColor(color)
                binding.tvSpent.setTextColor(color)
                binding.tvPercent.setTextColor(color)
            }
        }
    }
}