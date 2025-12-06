package com.example.gastos.adapters  // Ajusta el paquete

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gastos.R
import com.example.gastos.data.entities.Budget
import com.example.gastos.databinding.ItemBudgetBinding

class BudgetAdapter : RecyclerView.Adapter<BudgetAdapter.BudgetViewHolder>() {

    private var budgets: List<Budget> = emptyList()

    fun submitList(list: List<Budget>) {
        budgets = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudgetViewHolder {
        val binding = ItemBudgetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BudgetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BudgetViewHolder, position: Int) {
        holder.bind(budgets[position])
    }

    override fun getItemCount(): Int = budgets.size

    class BudgetViewHolder(private val binding: ItemBudgetBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(budget: Budget) {
            binding.tvCategory.text = budget.category
            binding.tvSpent.text = String.format("$%.2f", budget.spent)
            binding.tvLimit.text = String.format("de $%.2f", budget.monthlyLimit)

            val percent = if (budget.monthlyLimit > 0) ((budget.spent / budget.monthlyLimit) * 100).toInt() else 0
            binding.tvPercent.text = "$percent%"
            binding.tvRemaining.text = String.format("Quedan $%.2f disponibles", budget.monthlyLimit - budget.spent)

            // Ajusta barra de progreso
            val layoutParams = binding.progressFill.layoutParams
            layoutParams.width = (binding.progressBackground.width * percent / 100).coerceAtMost(binding.progressBackground.width)
            binding.progressFill.layoutParams = layoutParams

            // Colores dinámicos
            val color = when {
                percent >= 100 -> R.color.red_500
                percent >= 90 -> R.color.amber_500
                else -> R.color.emerald_400
            }
            binding.progressFill.setBackgroundColor(itemView.context.getColor(color))
            binding.tvSpent.setTextColor(itemView.context.getColor(color))
            binding.tvPercent.setTextColor(itemView.context.getColor(color))
        }
    }
}