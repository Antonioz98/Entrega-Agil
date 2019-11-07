package com.antonio.entregaagil.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.antonio.entregaagil.R
import com.antonio.entregaagil.ui.adapter.list.AssinantesAdapter
import com.antonio.entregaagil.ui.viewmodel.AssinantesViewModel
import kotlinx.android.synthetic.main.lista_assinantes.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class AssinantesActivity : AppCompatActivity() {

    private val adapter: AssinantesAdapter by inject()
    private val viewModel: AssinantesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lista_assinantes)

        confguraFAB()
        assinantes_recyclerView.adapter = adapter
    }

    private fun confguraFAB() {
        assinantes_fab.setOnClickListener {
            startActivity(Intent(this, CadastroClienteActivity::class.java))
        }
    }
}
