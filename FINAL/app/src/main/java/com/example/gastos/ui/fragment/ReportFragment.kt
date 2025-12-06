package com.example.gastos.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.gastos.R
import com.example.gastos.databinding.FragmentReportBinding
import com.example.gastos.viewmodels.ReportViewModel
import java.text.NumberFormat
import java.util.Locale

class ReportFragment : Fragment() {

    private var _binding: FragmentReportBinding? = null
    private val binding get() = _binding!!

    // CORRECTO: Especifica el tipo explícitamente
    private val viewModel: ReportViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupObservers()
        setupClickListeners()
    }

    private fun setupUI() {
        // Formateador de números
        val formatter = NumberFormat.getCurrencyInstance(Locale.US)

        // Configurar ViewModel
        viewModel.selectedMonth.observe(viewLifecycleOwner, Observer { month ->
            binding.tvSelectedMonth.text = getMonthName(month)
        })

        viewModel.selectedYear.observe(viewLifecycleOwner, Observer { year ->
            binding.tvSelectedYear.text = year.toString()
        })

        viewModel.totalIncome.observe(viewLifecycleOwner, Observer { income ->
            binding.tvTotalIncome.text = formatter.format(income)
        })

        viewModel.totalExpense.observe(viewLifecycleOwner, Observer { expense ->
            binding.tvTotalExpense.text = formatter.format(expense)
        })

        viewModel.netBalance.observe(viewLifecycleOwner, Observer { balance ->
            binding.tvNetBalance.text = formatter.format(balance)
            // Cambiar color según si es positivo o negativo
            if (balance >= 0) {
                binding.tvNetBalance.setTextColor(requireContext().getColor(R.color.emerald_400))
            } else {
                binding.tvNetBalance.setTextColor(requireContext().getColor(R.color.red_400))
            }
        })

        viewModel.dailyAverage.observe(viewLifecycleOwner, Observer { average ->
            binding.tvDailyAverage.text = formatter.format(average)
        })

        viewModel.incomePercent.observe(viewLifecycleOwner, Observer { percent ->
            binding.tvIngresosPercent.text = "$percent%"
            // Ajustar altura de la barra
            val params = binding.barIngresos.layoutParams
            params.height = (percent * 1.2).toInt()
            binding.barIngresos.layoutParams = params
        })

        viewModel.expensePercent.observe(viewLifecycleOwner, Observer { percent ->
            binding.tvGastosPercent.text = "$percent%"
            // Ajustar altura de la barra
            val params = binding.barGastos.layoutParams
            params.height = (percent * 1.2).toInt()
            binding.barGastos.layoutParams = params
        })
    }

    private fun setupObservers() {
        // Ya configurado en setupUI
    }

    private fun setupClickListeners() {
        // Navegación de meses
        binding.btnPrevMonth.setOnClickListener {
            viewModel.prevMonth()
        }

        binding.btnNextMonth.setOnClickListener {
            viewModel.nextMonth()
        }

        // Seleccionar mes
        binding.btnSelectMonth.setOnClickListener {
            // TODO: Implementar selector de fecha
            // Podrías mostrar un DatePickerDialog aquí
        }
    }

    private fun getMonthName(month: Int): String {
        return when (month) {
            1 -> "ENERO"
            2 -> "FEBRERO"
            3 -> "MARZO"
            4 -> "ABRIL"
            5 -> "MAYO"
            6 -> "JUNIO"
            7 -> "JULIO"
            8 -> "AGOSTO"
            9 -> "SEPTIEMBRE"
            10 -> "OCTUBRE"
            11 -> "NOVIEMBRE"
            12 -> "DICIEMBRE"
            else -> "MES"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}