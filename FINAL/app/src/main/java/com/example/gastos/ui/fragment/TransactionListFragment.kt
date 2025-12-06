package com.example.gastos.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gastos.adapters.TransactionAdapter
import com.example.gastos.databinding.FragmentTransactionListBinding
import com.example.gastos.viewmodels.TransactionViewModel

class TransactionListFragment : Fragment() {

    private var _binding: FragmentTransactionListBinding? = null
    private val binding get() = _binding!!

    // Usa activityViewModels para compartir el ViewModel con la Activity
    private val viewModel: TransactionViewModel by activityViewModels()

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

        // Configurar RecyclerView
        adapter = TransactionAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        // Observar la lista de transacciones
        viewModel.transactions.observe(viewLifecycleOwner) { transacciones ->
            if (transacciones.isNullOrEmpty()) {
                // Mostrar estado vacío
                showEmptyState()
            } else {
                // Mostrar lista ordenada
                showTransactionList(transacciones)
            }
        }

        // Configurar el botón de recargar - CORREGIDO: usa loadAllTransactions()
        binding.buttonReload.setOnClickListener {
            viewModel.loadAllTransactions()  // ¡Este es el nombre correcto!
        }

        // Opcional: Observar mensajes de error
        viewModel.errorMessage.observe(viewLifecycleOwner) { mensaje ->
            if (!mensaje.isNullOrBlank()) {
                // Mostrar error con Toast
                android.widget.Toast.makeText(
                    requireContext(),
                    mensaje,
                    android.widget.Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun showTransactionList(transacciones: List<com.example.gastos.data.entities.Transaction>) {
        binding.recyclerView.visibility = View.VISIBLE
        binding.textViewEmpty.visibility = View.GONE

        // Ordenar por fecha descendente (más recientes primero)
        val transaccionesOrdenadas = transacciones.sortedByDescending { it.date }
        adapter.submitList(transaccionesOrdenadas)
    }

    private fun showEmptyState() {
        binding.recyclerView.visibility = View.GONE
        binding.textViewEmpty.visibility = View.VISIBLE
        adapter.submitList(emptyList())
    }

    override fun onResume() {
        super.onResume()
        // Recargar datos cuando el fragmento se vuelve visible
        viewModel.loadAllTransactions()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}