package com.example.gastos.ui.fragments  // Ajusta el paquete

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.gastos.databinding.FragmentReportBinding
import com.example.gastos.viewmodels.ReportViewModel
import java.text.SimpleDateFormat
import java.util.*

class ReportFragment : Fragment() {

    private var _binding: FragmentReportBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ReportViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Navegación de meses
        binding.btnPrevMonth.setOnClickListener { navigateMonth(-1) }
        binding.btnNextMonth.setOnClickListener { navigateMonth(1) }

        // Observa mes/año y actualiza texto + gráfico
        viewModel.selectedMonth.observe(viewLifecycleOwner) { month ->
            val monthName = SimpleDateFormat("MMMM", Locale.getDefault()).format(Calendar.getInstance().apply { set(Calendar.MONTH, month - 1) }.time).uppercase()
            binding.tvSelectedMonth.text = monthName
            updateBars()
        }
        viewModel.selectedYear.observe(viewLifecycleOwner) { year ->
            binding.tvSelectedYear.text = year.toString()
        }

        // Observa estadísticas y actualiza textos
        viewModel.totalIncome.observe(viewLifecycleOwner) { binding.tvTotalIncome.text = String.format("$%.2f", it) }
        viewModel.totalExpense.observe(viewLifecycleOwner) { binding.tvTotalExpense.text = String.format("$%.2f", it) }
        viewModel.netBalance.observe(viewLifecycleOwner) { binding.tvNetBalance.text = String.format("$%.2f", it) }
        viewModel.dailyAverage.observe(viewLifecycleOwner) { binding.tvDailyAverage.text = String.format("$%.2f", it) }
        viewModel.incomePercent.observe(viewLifecycleOwner) { binding.tvIngresosPercent.text = "$it%" }
        viewModel.expensePercent.observe(viewLifecycleOwner) { binding.tvGastosPercent.text = "$it%" }
    }

    private fun navigateMonth(delta: Int) {
        val currentMonth = viewModel.selectedMonth.value ?: (Calendar.getInstance().get(Calendar.MONTH) + 1)
        val currentYear = viewModel.selectedYear.value ?: Calendar.getInstance().get(Calendar.YEAR)

        var newMonth = currentMonth + delta
        var newYear = currentYear

        if (newMonth > 12) {
            newMonth = 1
            newYear++
        } else if (newMonth < 1) {
            newMonth = 12
            newYear--
        }

        viewModel.setMonth(newMonth)
        viewModel.setYear(newYear)
    }

    private fun updateBars() {
        val incomePercent = viewModel.incomePercent.value ?: 0
        val expensePercent = viewModel.expensePercent.value ?: 0

        // Ajusta altura de barras dinámicamente (máx 120dp)
        val maxHeight = 120
        binding.barIngresos.layoutParams.height = (maxHeight * incomePercent / 100).coerceAtLeast(10)
        binding.barGastos.layoutParams.height = (maxHeight * expensePercent / 100).coerceAtLeast(10)
        binding.barIngresos.requestLayout()
        binding.barGastos.requestLayout()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}