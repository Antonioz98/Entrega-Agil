package com.antonio.entregaagil.ui.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearSmoothScroller
import com.antonio.entregaagil.R
import com.antonio.entregaagil.modelo.Rota
import com.antonio.entregaagil.rotas
import com.antonio.entregaagil.ui.activity.DELETAR_ROTA
import com.antonio.entregaagil.ui.activity.DetalhesRotaActivity
import com.antonio.entregaagil.ui.activity.ROTA_POSICAO
import com.antonio.entregaagil.ui.activity.ROTA_TAG
import com.antonio.entregaagil.ui.adapter.list.RotasAdapter
import com.antonio.entregaagil.ui.viewmodel.RotasViewModel
import kotlinx.android.synthetic.main.fragment_rotas.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

const val ALTERAR_ROTA = 14125

class RotasFragment : Fragment() {

    private val adapter: RotasAdapter by inject()
    private val viewModel: RotasViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_rotas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        (activity as AppCompatActivity).supportActionBar?.hide()
        confguraFAB()
        configuraAdapter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        (activity as AppCompatActivity).supportActionBar?.show()
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
        val intent = Intent(context, DetalhesRotaActivity::class.java)
        intent.putExtra(ROTA_TAG, rota)
        intent.putExtra(ROTA_POSICAO, posicao)
        startActivityForResult(intent, ALTERAR_ROTA)
    }

    private fun solicitaConfirmacaoParaExcluir(posicao: Int) {
        val builder = AlertDialog.Builder(context!!)

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
            val builder = AlertDialog.Builder(context!!)
            builder.setTitle("Descricao da nova rota")
            val input = EditText(context)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val rota = data?.getParcelableExtra<Rota>(ROTA_TAG)
            val posicao = data?.getIntExtra(ROTA_POSICAO, 0)
            if (requestCode == ALTERAR_ROTA) {
                if (data?.hasExtra(DELETAR_ROTA) == true) {
                    solicitaConfirmacaoParaExcluir(posicao!!)
                } else {
                    adapter.altera(rota, posicao)
                    Toast.makeText(context, "alterar", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun cadastraRota(descricao: String) {
        val rota = Rota()
        rota.descricao = descricao
        adapter.adiciona(rota)
    }

    private fun scrollToFinal() {
        val smoothScroller = object : LinearSmoothScroller(context) {
            override fun getVerticalSnapPreference(): Int {
                return SNAP_TO_START
            }
        }
        smoothScroller.targetPosition = adapter.itemCount
        rotas_recyclerView.layoutManager?.startSmoothScroll(smoothScroller)
    }
}
