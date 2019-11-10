package com.antonio.entregaagil.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.antonio.entregaagil.R
import com.antonio.entregaagil.modelo.Rota
import com.antonio.entregaagil.ui.adapter.list.AssinantesAdapter
import kotlinx.android.synthetic.main.detalhes_rota.*
import org.koin.android.ext.android.inject

const val ROTA_TAG = "ROTA_TAG"
const val ROTA_POSICAO = "ROTA_POSICAO"
const val DELETAR_ROTA = "deletar"

class DetalhesRotaActivity :AppCompatActivity() {

    private val adapter: AssinantesAdapter by inject()
    private var rota = Rota()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detalhes_rota)

        rota = intent.getParcelableExtra(ROTA_TAG)
        title = rota.descricao
        configuraAdapter()

    }

    private fun configuraAdapter() {
        detalhes_rota_assinantes_recyclerView.adapter = adapter
        val assinantes = rota.assinantes
        adapter.atualizaLista(assinantes)
        adapter.clickListener = { assinante, posicao ->
//            abreFormularioPreenchido(assinante, posicao)
        }
    }
}