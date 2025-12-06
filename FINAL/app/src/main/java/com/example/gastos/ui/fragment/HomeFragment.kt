package com.example.gastos.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.gastos.databinding.FragmentHomeBinding
import com.example.gastos.viewmodels.HomeViewModel
import com.example.gastos.R

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Cargar datos iniciales
        viewModel.refreshData()

        // Observar datos
        viewModel.balance.observe(viewLifecycleOwner) { balance ->
            binding.tvSaldo.text = if (balance >= 0) {
                String.format("$%.2f", balance)
            } else {
                String.format("-$%.2f", -balance)
            }
        }

        viewModel.income.observe(viewLifecycleOwner) {
            binding.tvIngresos.text = String.format("$%.2f", it)
        }

        viewModel.expenses.observe(viewLifecycleOwner) {
            binding.tvGastos.text = String.format("$%.2f", it)
        }

        // Navegación - DOS OPCIONES:

        // OPCIÓN A: Con acciones (si las tienes definidas)
        binding.cardNuevaTransaccion.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_transaction)
        }

        // OPCIÓN B: Directa por ID (más simple)
        binding.cardHistorial.setOnClickListener {
            findNavController().navigate(R.id.transactionListFragment)
        }

        binding.cardPresupuestos.setOnClickListener {
            findNavController().navigate(R.id.budgetFragment)
        }

        binding.cardReportes.setOnClickListener {
            findNavController().navigate(R.id.reportFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        // Refrescar datos cuando vuelvas a este fragment
        viewModel.refreshData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}