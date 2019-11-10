package com.antonio.entregaagil.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.antonio.entregaagil.R
import com.antonio.entregaagil.extension.formataDataResumida
import com.antonio.entregaagil.modelo.Assinante
import com.antonio.entregaagil.ui.dialog.PegarDataDialog
import kotlinx.android.synthetic.main.formulario_assinante.*
import java.util.*

const val ASSINANTE_TAG = "ASSINANTE_INTENT"
const val ASSINANTE_POSICAO = "ASSINANTE_POSICAO"
const val DELETAR_ASSINANTE = "deletar"

class FormularioAssinanteActivity : AppCompatActivity() {

    private val DATE_PICKER_FRAGMENT = "PEGAR_DATA_FRAGMENT"
    private val dataInicio = Calendar.getInstance()
    private val dataFim = Calendar.getInstance()
    private var assinante = Assinante()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.formulario_assinante)

        preencheCampos()
        title = "Formulario de assinante"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        configurarDataFinal()
        configurarDataInicial()
        configuraBotaoSalvar()
    }

    private fun configuraBotaoSalvar() {
        cadastro_cliente_salvar.setOnClickListener {
            assinante.nome = cadastro_cliente_nome.text.toString()
            assinante.endereco = cadastro_cliente_endereco.text.toString()
//            assinante.numero = cadastro_cliente_numero.text.toString().toInt()
            assinante.bairro = cadastro_cliente_bairro.text.toString()
            //falta converter a data

            val posicao = intent.getIntExtra(ASSINANTE_POSICAO, 0)
            val intent = Intent()
            intent.putExtra(ASSINANTE_TAG, assinante)
            intent.putExtra(ASSINANTE_POSICAO, posicao)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    private fun preencheCampos() {
        if (intent.hasExtra(ASSINANTE_TAG)) {
            assinante = intent.getParcelableExtra(ASSINANTE_TAG)

            cadastro_cliente_nome.setText(assinante.nome)
            cadastro_cliente_endereco.setText(assinante.endereco)
            cadastro_cliente_numero.setText(assinante.numero.toString())
            cadastro_cliente_bairro.setText(assinante.bairro)
            cadastro_cliente_inicio_assinatura.setText(
                assinante.inicioAssinatura?.formataDataResumida() ?: "__/__/____"
            )
            cadastro_cliente_termino_assinatura.setText(
                assinante.fimAssinatura?.formataDataResumida() ?: "__/__/____"
            )
            cadastro_cliente_complementar.setText(assinante.complemento)
        }
    }

    private fun configurarDataInicial() {
        cadastro_cliente_inicio_assinatura.setOnClickListener {
            PegarDataDialog { _, ano, mes, dia ->
                dataInicio.set(ano, mes, dia)
                cadastro_cliente_inicio_assinatura.setText(dataInicio.time.formataDataResumida())
            }.show(supportFragmentManager, DATE_PICKER_FRAGMENT)

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu_formulario_assinante, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.menu_deletar -> deletar()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deletar() {
        val posicao = intent.getIntExtra(ASSINANTE_POSICAO, 0)
        val intent = Intent()
        intent.putExtra(ASSINANTE_TAG, assinante)
        intent.putExtra(ASSINANTE_POSICAO, posicao)
        intent.putExtra(DELETAR_ASSINANTE, true)
        setResult(Activity.RESULT_OK, intent)
        finish()
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

