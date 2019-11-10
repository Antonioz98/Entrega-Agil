package com.antonio.entregaagil.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearSmoothScroller
import com.antonio.entregaagil.R
import com.antonio.entregaagil.modelo.Rota
import com.antonio.entregaagil.rotas
import com.antonio.entregaagil.ui.adapter.list.RotasAdapter
import com.antonio.entregaagil.ui.viewmodel.RotasViewModel
import kotlinx.android.synthetic.main.lista_rotas.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

const val ALTERAR_ROTA = 14125

class RotasActivity : AppCompatActivity() {

    private val adapter: RotasAdapter by inject()
    private val viewModel: RotasViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lista_rotas)

        confguraFAB()
        configuraAdapter()
    }

    private fun configuraAdapter() {
        rotas_recyclerView.adapter = adapter
        val rotas = rotas()
        adapter.atualizaLista(rotas)
        adapter.clickListener = { rota, posicao ->
            abreDetalheDaRota(rota, posicao)
        }
    }

    private fun abreDetalheDaRota(rota: Rota, posicao: Int) {
        val intent = Intent(this, DetalhesRotaActivity::class.java)
        intent.putExtra(ROTA_TAG, rota)
        intent.putExtra(ROTA_POSICAO, posicao)
        startActivityForResult(intent, ALTERAR_ROTA)
    }

    private fun solicitaConfirmacaoParaExcluir(posicao: Int) {
        val builder = AlertDialog.Builder(this)

        builder.apply {
            setTitle("Confirmação")
            setMessage("Esta rota será excluído, deseja continuar?")
            setPositiveButton("Sim") { _, _ ->
                adapter.exclui(posicao)
            }
            setNegativeButton("nao") { _, _ -> }
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun confguraFAB() {
        rotas_fab.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Descricao da nova rota")
            val input = EditText(this)
            input.inputType = InputType.TYPE_CLASS_TEXT

            val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            input.layoutParams = lp
            builder.setView(input)
            builder.setPositiveButton("Cadastrar") { dialog, which ->
                cadastraRota(input.text.toString())
                scrollToFinal()
            }
            builder.setNegativeButton("Cancelar") { dialog, which -> dialog.cancel() }

            builder.show()
        }
    }

    private fun cadastraRota(descricao: String) {
        val rota = Rota()
        rota.descricao = descricao
        adapter.adiciona(rota)
    }

    private fun scrollToFinal() {
        val smoothScroller = object : LinearSmoothScroller(this) {
            override fun getVerticalSnapPreference(): Int {
                return SNAP_TO_START
            }
        }
        smoothScroller.targetPosition = adapter.itemCount
        rotas_recyclerView.layoutManager?.startSmoothScroll(smoothScroller)
    }
}
