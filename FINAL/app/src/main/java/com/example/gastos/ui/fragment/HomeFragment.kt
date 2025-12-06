package com.tupaquete.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.tupaquete.R
import com.tupaquete.databinding.FragmentHomeBinding
import java.text.NumberFormat
import java.util.*

class HomeFragment : Fragment() {

    // ViewBinding para acceder a las vistas
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // Datos de ejemplo para mostrar
    private val datosEjemplo = mapOf(
        "saldo" to 1450.00,
        "ingresos" to 2500.00,
        "gastos" to 1050.00
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflar el layout usando ViewBinding
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Cargar datos de ejemplo
        cargarDatosEjemplo()

        // Configurar listeners para los botones de acceso rápido
        configurarAccesosRapidos()
    }

    private fun cargarDatosEjemplo() {
        // Formatear números como dinero
        val formatoDinero = NumberFormat.getCurrencyInstance().apply {
            maximumFractionDigits = 2
            currency = Currency.getInstance("USD")
        }

        // Obtener datos
        val saldo = datosEjemplo["saldo"] ?: 0.0
        val ingresos = datosEjemplo["ingresos"] ?: 0.0
        val gastos = datosEjemplo["gastos"] ?: 0.0

        // Mostrar en pantalla
        binding.tvSaldo.text = formatoDinero.format(saldo)
        binding.tvIngresos.text = formatoDinero.format(ingresos)
        binding.tvGastos.text = formatoDinero.format(gastos)

        // Opcional: Calcular y mostrar diferencia
        val balance = ingresos - gastos
        if (balance >= 0) {
            // Mensaje positivo
            // Puedes agregar un TextView para mostrar mensajes
        } else {
            // Mensaje de advertencia
        }
    }

    private fun configurarAccesosRapidos() {
        // 1. Nueva Transacción
        binding.cardNuevaTransaccion.setOnClickListener {
            // Navegar a la pantalla de agregar transacción
            findNavController().navigate(R.id.action_homeFragment_to_addTransactionFragment)
        }

        // 2. Historial de Transacciones
        binding.cardHistorial.setOnClickListener {
            // Navegar a la pantalla de transacciones/historial
            findNavController().navigate(R.id.action_homeFragment_to_transactionsFragment)
        }

        // 3. Presupuestos
        binding.cardPresupuestos.setOnClickListener {
            // Navegar a la pantalla de presupuestos
            findNavController().navigate(R.id.action_homeFragment_to_budgetsFragment)
        }

        // 4. Reportes
        binding.cardReportes.setOnClickListener {
            // Navegar a la pantalla de reportes
            findNavController().navigate(R.id.action_homeFragment_to_reportsFragment)
        }
    }

    // Función para actualizar datos (puedes llamarla cuando se agregue una transacción)
    fun actualizarDatos(nuevoSaldo: Double, nuevosIngresos: Double, nuevosGastos: Double) {
        // Formatear números como dinero
        val formatoDinero = NumberFormat.getCurrencyInstance().apply {
            maximumFractionDigits = 2
            currency = Currency.getInstance("USD")
        }

        binding.tvSaldo.text = formatoDinero.format(nuevoSaldo)
        binding.tvIngresos.text = formatoDinero.format(nuevosIngresos)
        binding.tvGastos.text = formatoDinero.format(nuevosGastos)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Limpiar binding para evitar memory leaks
        _binding = null
    }
}