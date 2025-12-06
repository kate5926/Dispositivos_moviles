package com.example.gastos.ui.fragments  // Ajusta el paquete

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gastos.adapters.TransactionAdapter
import com.example.gastos.databinding.FragmentTransactionListBinding
import com.example.gastos.viewmodels.TransactionViewModel

class TransactionListFragment : Fragment() {

    private var _binding: FragmentTransactionListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TransactionViewModel by viewModels()
    private lateinit var adapter: TransactionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransactionListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configura RecyclerView
        adapter = TransactionAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        // Observa la lista de transacciones y actualiza el adapter
        viewModel.transactions.observe(viewLifecycleOwner) { transactions ->
            adapter.submitList(transactions.sortedByDescending { it.date })  // Ordena por fecha descendente
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}