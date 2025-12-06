package com.example.gastos.adapters  // Ajusta el paquete

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gastos.R
import com.example.gastos.data.entities.Transaction
import com.example.gastos.databinding.ItemTransactionBinding
import java.text.SimpleDateFormat
import java.util.*

class TransactionAdapter : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    private var transactions: List<Transaction> = emptyList()

    fun submitList(list: List<Transaction>) {
        transactions = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding = ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(transactions[position])
    }

    override fun getItemCount(): Int = transactions.size

    class TransactionViewHolder(private val binding: ItemTransactionBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(transaction: Transaction) {
            // Descripción
            binding.tvDescription.text = transaction.description

            // Categoría y fecha
            val dateFormat = SimpleDateFormat("dd MMM", Locale.getDefault())
            binding.tvCategory.text = "${transaction.category} • ${dateFormat.format(transaction.date)}"

            // Monto y color (verde para ingreso, rojo para gasto)
            val amountText = if (transaction.isIncome) "+$ ${transaction.amount}" else "-$ ${transaction.amount}"
            binding.tvAmount.text = amountText
            binding.tvAmount.setTextColor(
                itemView.context.getColor(if (transaction.isIncome) R.color.emerald_400 else R.color.red_400)
            )

        }
    }
}