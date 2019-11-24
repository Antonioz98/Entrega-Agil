package com.antonio.entregaagil.ui.activity

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.antonio.entregaagil.R
import com.antonio.entregaagil.modelo.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.main_nav_view)

        val navController = findNavController(R.id.main_nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_rotas, R.id.navigation_assinantes, R.id.navigation_configuracao
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            destination.id
            when (destination.id) {
                R.id.navigation_formulario_assinante -> {
                    navView.visibility = GONE
                }
                R.id.navigation_detalhes_rota -> {
                    navView.visibility = GONE
                }
                else -> {
                    navView.visibility = VISIBLE
                }
            }
        }
        supportActionBar?.hide()
    }
}
