package com.example.gastos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.gastos.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflar el layout usando View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar la navegación
        setupNavigation()
    }

    private fun setupNavigation() {
        // Obtener el NavHostFragment
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment

        // Obtener el NavController
        val navController = navHostFragment.navController

        // Configurar BottomNavigationView con el NavController
        val bottomNavView: BottomNavigationView = binding.bottomNavView
        bottomNavView.setupWithNavController(navController)

        // Configurar el FAB (si decides agregarlo después)
        setupFloatingActionButton(navController)
    }

    private fun setupFloatingActionButton(navController: androidx.navigation.NavController) {
        // Por ahora vacío, puedes agregar funcionalidad después
        // binding.fab.setOnClickListener {
        //     navController.navigate(R.id.action_to_transaction_fragment)
        // }
    }

    // Manejar el botón de retroceso con Navigation Component
    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment
        return navHostFragment.navController.navigateUp() || super.onSupportNavigateUp()
    }
}