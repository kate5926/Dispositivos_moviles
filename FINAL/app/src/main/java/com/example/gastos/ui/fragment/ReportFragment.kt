package com.tupaquete.ui.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tupaquete.R
import com.tupaquete.databinding.FragmentReportsBinding
import java.text.NumberFormat
import java.util.*

class ReportsFragment : Fragment() {

    private var _binding: FragmentReportsBinding? = null
    private val binding get() = _binding!!

    // Fecha actual
    private var selectedMonth = Calendar.getInstance().get(Calendar.MONTH)
    private var selectedYear = Calendar.getInstance().get(Calendar.YEAR)

    // Datos de ejemplo organizados por mes/año
    private val reportData = mutableMapOf<String, Map<String, Double>>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializar datos de ejemplo
        initSampleData()

        // Configurar listeners
        setupDateListeners()

        // Mostrar datos iniciales (mes actual)
        updateDisplayedDate()
        loadReportData()
    }

    private fun initSampleData() {
        // Datos para diferentes meses
        reportData["11-2024"] = mapOf(
            "ingresos" to 2500.0,
            "gastos" to 1450.0,
            "balance" to 1050.0
        )

        reportData["12-2024"] = mapOf(
            "ingresos" to 2800.0,
            "gastos" to 1200.0,
            "balance" to 1600.0
        )

        reportData["1-2023"] = mapOf(
            "ingresos" to 2300.0,
            "gastos" to 1800.0,
            "balance" to 500.0
        )

        reportData["2-2023"] = mapOf(
            "ingresos" to 2400.0,
            "gastos" to 1900.0,
            "balance" to 500.0
        )
    }

    private fun setupDateListeners() {
        // Botón para seleccionar fecha (calendario)
        binding.btnSelectMonth.setOnClickListener {
            showDatePickerDialog()
        }

        // Botón mes anterior
        binding.btnPrevMonth.setOnClickListener {
            moveToPreviousMonth()
        }

        // Botón mes siguiente
        binding.btnNextMonth.setOnClickListener {
            moveToNextMonth()
        }
    }

    private fun showDatePickerDialog() {
        // Mostrar diálogo de selección de fecha (solo mes y año)
        val datePicker = DatePickerDialog(
            requireContext(),
            { _, year, month, _ ->
                // Solo necesitamos mes y año
                selectedYear = year
                selectedMonth = month
                updateDisplayedDate()
                loadReportData()
            },
            selectedYear,
            selectedMonth,
            1 // Día fijo (1)
        )

        // Configurar título
        datePicker.setTitle("Seleccionar mes y año")

        // Mostrar diálogo
        datePicker.show()
    }

    private fun moveToPreviousMonth() {
        selectedMonth--
        if (selectedMonth < 0) {
            selectedMonth = 11
            selectedYear--
        }
        updateDisplayedDate()
        loadReportData()
    }

    private fun moveToNextMonth() {
        selectedMonth++
        if (selectedMonth > 11) {
            selectedMonth = 0
            selectedYear++
        }
        updateDisplayedDate()
        loadReportData()
    }

    private fun updateDisplayedDate() {
        // Actualizar texto con el mes y año seleccionados
        val monthName = getMonthName(selectedMonth + 1)
        binding.tvSelectedMonth.text = monthName.uppercase()
        binding.tvSelectedYear.text = selectedYear.toString()

        // Actualizar también el botón de calendario (si usas Opción 1)
        binding.tvSelectedDate?.text = "$monthName $selectedYear"
    }

    private fun loadReportData() {
        // Obtener clave para buscar datos
        val dataKey = "${selectedMonth + 1}-$selectedYear"
        val data = reportData[dataKey]

        if (data != null) {
            // Mostrar datos del mes seleccionado
            displayReportData(data)
        } else {
            // Mostrar datos vacíos o mensaje
            displayEmptyData()
        }
    }

    private fun displayReportData(data: Map<String, Double>) {
        val ingresos = data["ingresos"] ?: 0.0
        val gastos = data["gastos"] ?: 0.0
        val balance = data["balance"] ?: 0.0

        // Formatear como dinero
        val formato = NumberFormat.getCurrencyInstance().apply {
            maximumFractionDigits = 2
            currency = Currency.getInstance("USD")
        }

        // Actualizar UI
        binding.tvTotalIncome.text = formato.format(ingresos)
        binding.tvTotalExpense.text = formato.format(gastos)
        binding.tvNetBalance.text = formato.format(balance)

        // Calcular y mostrar porcentajes
        val total = ingresos + gastos
        if (total > 0) {
            val porcentajeGastos = (gastos / total * 100).toInt()
            val porcentajeIngresos = (ingresos / total * 100).toInt()

            binding.tvGastosPercent.text = "$porcentajeGastos%"
            binding.tvIngresosPercent.text = "$porcentajeIngresos%"

            // Ajustar altura de barras
            binding.barGastos.layoutParams.height = (porcentajeGastos * 1.5).dpToPx()
            binding.barIngresos.layoutParams.height = (porcentajeIngresos * 1.5).dpToPx()
        }

        // Mostrar mensaje según balance
        val mensaje = when {
            balance > 500 -> "¡Excelente! Buen balance este mes. 💪"
            balance > 0 -> "Bien, balance positivo. 👍"
            balance == 0.0 -> "Balance neutro. ⚖️"
            else -> "Cuidado, balance negativo. 💡"
        }

        // Actualizar mensaje (si tienes TextView para eso)
        binding.tvAdviceMessage?.text = mensaje
    }

    private fun displayEmptyData() {
        // Mostrar datos vacíos
        binding.tvTotalIncome.text = "$0.00"
        binding.tvTotalExpense.text = "$0.00"
        binding.tvNetBalance.text = "$0.00"

        binding.tvGastosPercent.text = "0%"
        binding.tvIngresosPercent.text = "0%"

        // Barras mínimas
        binding.barGastos.layoutParams.height = 20.dpToPx()
        binding.barIngresos.layoutParams.height = 20.dpToPx()

        // Mensaje
        binding.tvAdviceMessage?.text = "No hay datos para este período. 📊"
    }

    private fun getMonthName(month: Int): String {
        return when (month) {
            1 -> "Enero"; 2 -> "Febrero"; 3 -> "Marzo"; 4 -> "Abril"
            5 -> "Mayo"; 6 -> "Junio"; 7 -> "Julio"; 8 -> "Agosto"
            9 -> "Septiembre"; 10 -> "Octubre"; 11 -> "Noviembre"; 12 -> "Diciembre"
            else -> "Mes $month"
        }
    }

    private fun Int.dpToPx(): Int {
        val density = resources.displayMetrics.density
        return (this * density).toInt()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}