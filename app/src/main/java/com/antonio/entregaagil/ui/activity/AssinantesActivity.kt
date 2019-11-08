package com.antonio.entregaagil.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.antonio.entregaagil.R
import com.antonio.entregaagil.assinantes
import com.antonio.entregaagil.modelo.Assinante
import com.antonio.entregaagil.ui.adapter.list.AssinantesAdapter
import com.antonio.entregaagil.ui.viewmodel.AssinantesViewModel
import kotlinx.android.synthetic.main.lista_assinantes.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

const val ALTERAR_ASSINANTE = 15125
const val ADICIONAR_ASSINANTE = 15126

class AssinantesActivity : AppCompatActivity() {

    private val adapter: AssinantesAdapter by inject()
    private val viewModel: AssinantesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lista_assinantes)

        confguraFAB()
        configuraAdapter()
    }

    private fun configuraAdapter() {
        assinantes_recyclerView.adapter = adapter
        val assinantes = assinantes()
        adapter.atualizaLista(assinantes)
        adapter.clickListener = { assinante, posicao ->
            abreFormularioPreenchido(assinante, posicao)
        }
    }

    private fun abreFormularioPreenchido(assinante: Assinante, posicao: Int) {
        val intent = Intent(this, FormularioAssinanteActivity::class.java)
        intent.putExtra(ASSINANTE_TAG, assinante)
        intent.putExtra(ASSINANTE_POSICAO, posicao)
        startActivityForResult(intent, ALTERAR_ASSINANTE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val assinante = data?.getParcelableExtra<Assinante>(ASSINANTE_TAG)
            val posicao = data?.getIntExtra(ASSINANTE_POSICAO, 0)
            if (requestCode == ALTERAR_ASSINANTE) {
                adapter.altera(assinante, posicao)
                Toast.makeText(this, "alterar", Toast.LENGTH_SHORT).show()
            }
            if (requestCode == ADICIONAR_ASSINANTE) {
                adapter.adiciona(assinante)
                Toast.makeText(this, "adicionar", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun confguraFAB() {
        assinantes_fab.setOnClickListener {
            val intent = Intent(this, FormularioAssinanteActivity::class.java)
            startActivityForResult(intent, ADICIONAR_ASSINANTE)
        }
    }
}
