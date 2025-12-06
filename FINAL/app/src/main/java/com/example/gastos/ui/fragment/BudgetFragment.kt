package com.example.gastos.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gastos.adapters.BudgetAdapter
import com.example.gastos.data.entities.Budget
import com.example.gastos.databinding.FragmentBudgetBinding

class BudgetFragment : Fragment() {

    private var _binding: FragmentBudgetBinding? = null
    private val binding get() = _binding!!
    private val adapter = BudgetAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBudgetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar RecyclerView
        binding.recyclerViewBudgets.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewBudgets.adapter = adapter
/*
        // Datos de ejemplo (deberían venir de un ViewModel)
        val budgets = listOf(
            Budget("Comida", 150.0, 200.0),
            Budget("Transporte", 80.0, 100.0),
            Budget("Entretenimiento", 120.0, 100.0),
            Budget("Compras", 300.0, 500.0),
            Budget("Salud", 50.0, 150.0)
        )

        adapter.submitList(budgets)  */


        binding.btnAddBudget.setOnClickListener {
              }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}