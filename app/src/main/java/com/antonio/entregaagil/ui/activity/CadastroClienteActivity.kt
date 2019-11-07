package com.antonio.entregaagil.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.antonio.entregaagil.R
import com.antonio.entregaagil.extension.formataDataResumida
import com.antonio.entregaagil.ui.dialog.PegarDataDialog
import com.antonio.entregaagil.ui.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.cadastro_cliente.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class CadastroClienteActivity : AppCompatActivity() {

    private val DATE_PICKER_FRAGMENT = "PEGAR_DATA_FRAGMENT"
    private val dataInicio = Calendar.getInstance()
    private val dataFim = Calendar.getInstance()
    private val mainViewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cadastro_cliente)

        title = "Cadastro de cliente"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        mainViewModel
        configurarDataFinal()
        configurarDataInicial()
    }

    private fun configurarDataInicial() {
        cadastro_cliente_inicio_assinatura.setOnClickListener {
            PegarDataDialog { _, ano, mes, dia ->
                dataInicio.set(ano, mes, dia)
                cadastro_cliente_inicio_assinatura.setText(dataInicio.time.formataDataResumida())
            }.show(supportFragmentManager, DATE_PICKER_FRAGMENT)

        }
    }

    private fun configurarDataFinal() {
        cadastro_cliente_termino_assinatura.setOnClickListener {
            PegarDataDialog { _, ano, mes, dia ->
                dataFim.set(ano, mes, dia)
                cadastro_cliente_termino_assinatura.setText(dataFim.time.formataDataResumida())
            }.show(supportFragmentManager, DATE_PICKER_FRAGMENT)
        }
    }
}

