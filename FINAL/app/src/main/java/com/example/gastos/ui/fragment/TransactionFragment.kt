package com.example.gastos.ui.fragments  // Ajusta el paquete

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.gastos.databinding.FragmentTransactionBinding
import com.example.gastos.viewmodels.TransactionViewModel
import java.util.*

class AddTransactionFragment : Fragment() {

    private var _binding: FragmentTransactionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TransactionViewModel by viewModels()

    // Categorías fijas (puedes editar o cargar dinámicamente)
    private val categories = arrayOf("Comida", "Transporte", "Entretenimiento", "Salud", "Educación", "Otros")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // En onViewCreated
        val adapter = CategorySpinnerAdapter(requireContext(), categories)
        binding.spinnerCategoria.adapter = adapter  // Asumiendo que agregaste el Spinner al XML

        // Observa si el guardado fue exitoso
        viewModel.saveSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                Toast.makeText(requireContext(), "Transacción guardada", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()  // Vuelve a Home
            }
        }

        // Click en botón guardar
        binding.btnGuardar.setOnClickListener {
            saveTransaction()
        }
    }

    private fun saveTransaction() {
        val isIncome = binding.rgTipo.checkedRadioButtonId == R.id.rbIngreso
        val amountText = binding.etMonto.text.toString()
        val description = binding.etDescripcion.text.toString()
        // val category = binding.spinnerCategoria.selectedItem.toString()  // Si agregas Spinner al XML

        // Validación básica
        if (amountText.isBlank()) {
            Toast.makeText(requireContext(), "Ingresa un monto", Toast.LENGTH_SHORT).show()
            return
        }

        val amount = amountText.toDoubleOrNull()
        if (amount == null || amount <= 0) {
            Toast.makeText(requireContext(), "Monto inválido", Toast.LENGTH_SHORT).show()
            return
        }

        // Guarda usando ViewModel (fecha actual, categoría por defecto si no hay Spinner)
        val category = "Otros"  // Cambia si tienes Spinner
        viewModel.saveTransaction(amount, category, Date(), description, isIncome)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}