package com.example.gastos.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.gastos.R
import androidx.navigation.fragment.findNavController
import com.example.gastos.databinding.FragmentTransactionBinding
import com.example.gastos.viewmodels.TransactionViewModel
import java.util.*

class TransactionFragment : Fragment() {

    private var _binding: FragmentTransactionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TransactionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar listeners de RadioButtons para cambiar color dinámico
        setupRadioButtons()

        // Observar si el guardado fue exitoso
        viewModel.saveSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                Toast.makeText(requireContext(), "✓ Movimiento guardado", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()  // Vuelve atrás
            }
        }

        // Observar errores
        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            if (error.isNotBlank()) {
                Toast.makeText(requireContext(), "✗ $error", Toast.LENGTH_SHORT).show()
            }
        }

        // Click en botón guardar
        binding.btnGuardar.setOnClickListener {
            saveTransaction()
        }

        // Hacer foco automático en el campo de monto
        binding.etMonto.requestFocus()
    }

    private fun setupRadioButtons() {
        // Configurar color dinámico para radio buttons
        binding.rgTipo.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rbIngreso -> {
                    // Cambiar color del título cuando es ingreso
                    binding.tvFormTitulo.setTextColor(
                        requireContext().getColor(android.R.color.holo_green_light)
                    )
                    binding.btnGuardar.text = "GUARDAR INGRESO"
                }
                R.id.rbGasto -> {
                    // Color normal para gasto
                    binding.tvFormTitulo.setTextColor(
                        requireContext().getColor(com.example.gastos.R.color.text_primary)
                    )
                    binding.btnGuardar.text = "GUARDAR GASTO"
                }
            }
        }
    }

    private fun saveTransaction() {
        // Obtener datos del formulario
        val isIncome = binding.rgTipo.checkedRadioButtonId == R.id.rbIngreso
        val amountText = binding.etMonto.text.toString().replace(",", ".")
        val description = binding.etDescripcion.text.toString().trim()

        // Validaciones
        if (amountText.isBlank()) {
            showError("Ingresa un monto")
            binding.etMonto.requestFocus()
            return
        }

        val amount = amountText.toDoubleOrNull()
        if (amount == null || amount <= 0) {
            showError("Monto inválido. Ej: 25.50")
            binding.etMonto.requestFocus()
            return
        }

        if (description.isBlank()) {
            showError("Ingresa una descripción")
            binding.etDescripcion.requestFocus()
            return
        }

        if (description.length < 3) {
            showError("Descripción muy corta (mínimo 3 letras)")
            binding.etDescripcion.requestFocus()
            return
        }

        // Asignar categoría automáticamente basada en descripción
        val category = assignCategory(description)

        // Mostrar mensaje de procesamiento
        binding.btnGuardar.text = if (isIncome) "GUARDANDO INGRESO..." else "GUARDANDO GASTO..."
        binding.btnGuardar.isEnabled = false

        // Guardar transacción usando ViewModel
        viewModel.saveTransaction(
            amount = amount,
            category = category,
            date = Date(),
            description = description,
            isIncome = isIncome,
            note = ""  // Puedes agregar campo de nota si quieres
        )
    }

    private fun assignCategory(description: String): String {
        val descLower = description.lowercase(Locale.getDefault())

        return when {
            descLower.contains("comida") || descLower.contains("restaurante") ||
                    descLower.contains("supermercado") || descLower.contains("almuerzo") ||
                    descLower.contains("cena") || descLower.contains("desayuno") -> "Comida"

            descLower.contains("gasolina") || descLower.contains("transporte") ||
                    descLower.contains("taxi") || descLower.contains("bus") ||
                    descLower.contains("uber") || descLower.contains("metro") ||
                    descLower.contains("gas") || descLower.contains("auto") -> "Transporte"

            descLower.contains("cine") || descLower.contains("netflix") ||
                    descLower.contains("spotify") || descLower.contains("entretenimiento") ||
                    descLower.contains("película") || descLower.contains("música") ||
                    descLower.contains("videojuego") || descLower.contains("bar") -> "Entretenimiento"

            descLower.contains("doctor") || descLower.contains("farmacia") ||
                    descLower.contains("hospital") || descLower.contains("medicina") ||
                    descLower.contains("fisio") || descLower.contains("dentista") -> "Salud"

            descLower.contains("libro") || descLower.contains("curso") ||
                    descLower.contains("universidad") || descLower.contains("educación") ||
                    descLower.contains("clase") || descLower.contains("taller") -> "Educación"

            descLower.contains("ropa") || descLower.contains("zapato") ||
                    descLower.contains("tienda") || descLower.contains("mercado") ||
                    descLower.contains("compra") -> "Compras"

            else -> "Otros"
        }
    }

    private fun showError(message: String) {
        // Mostrar toast con estilo de error
        val toast = Toast.makeText(requireContext(), "⚠ $message", Toast.LENGTH_SHORT)
        toast.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}