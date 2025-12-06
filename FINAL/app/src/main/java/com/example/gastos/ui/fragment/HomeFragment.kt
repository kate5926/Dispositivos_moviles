package com.example.gastos.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.gastos.databinding.FragmentHomeBinding
import com.example.gastos.viewmodels.HomeViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.balance.observe(viewLifecycleOwner) { binding.tvSaldo.text = "$ %.2f".format(it) }
        viewModel.income.observe(viewLifecycleOwner) { binding.tvIngresos.text = "$ %.2f".format(it) }
        viewModel.expenses.observe(viewLifecycleOwner) { binding.tvGastos.text = "$ %.2f".format(it) }

        binding.cardNuevaTransaccion.setOnClickListener { findNavController().navigate(R.id.action_home_to_transaction) }
        binding.cardHistorial.setOnClickListener { findNavController().navigate(R.id.action_home_to_transaction_list) }
        binding.cardPresupuestos.setOnClickListener { findNavController().navigate(R.id.action_home_to_budget) }
        binding.cardReportes.setOnClickListener { findNavController().navigate(R.id.action_home_to_report) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}