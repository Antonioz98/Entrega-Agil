package com.antonio.entregaagil.ui.fragment

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.antonio.entregaagil.R
import com.antonio.entregaagil.constante.ASSINANTES_POR_ROTA
import com.antonio.entregaagil.constante.ASSINANTE_TAG
import com.antonio.entregaagil.constante.ROTA_TAG
import com.antonio.entregaagil.modelo.Assinante
import com.antonio.entregaagil.modelo.Rota
import com.antonio.entregaagil.ui.adapter.list.AssinantesAdapter
import com.antonio.entregaagil.ui.adapter.list.AssinantesAdapter2
import com.antonio.entregaagil.ui.adapter.list.DetalhesRotaAdapter
import com.antonio.entregaagil.ui.adapter.viewholder.DESVINCULAR
import com.antonio.entregaagil.ui.adapter.viewholder.NORMAL
import com.antonio.entregaagil.ui.viewmodel.RotasViewModel
import kotlinx.android.synthetic.main.fragment_detalhes_rota.*
import kotlinx.android.synthetic.main.procurar_assinantes.view.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class DetalhesRotaFragment : Fragment() {

    private val adapterAssinates: AssinantesAdapter2 by inject()
    private val adapter: DetalhesRotaAdapter by inject()
    private val viewModel: RotasViewModel by viewModel()
    private var rota = Rota()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detalhes_rota, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)

        rota = arguments?.getParcelable(ROTA_TAG)!!
        viewModel.getRota(rota).observe(this, Observer {
            it?.let {
                fragment_detalhes_rota_titulo.text = it.descricao
                fragment_detalhes_rota_quantida.text = "Assinantes: ${it.assinantes.size}/$ASSINANTES_POR_ROTA"
                adapter.atualizaLista(it.assinantes)
            }
        })


        configuraAdapter()
        confguraFab()
        configuraImagens()
    }

    private fun configuraImagens() {
        menu_excluir.setOnClickListener {
            solicitaConfirmacaoParaExcluir()
        }
        menu_editar.setOnClickListener {
            alteraDescricao()
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).supportActionBar?.show()
    }

    private fun confguraFab() {
        fragment_detalhes_rota_fab.setOnClickListener {
            if (rota.assinantes.size < 9) {
                val view = layoutInflater.inflate(R.layout.procurar_assinantes, null, false)
                view.procurar_assinantes_lista.adapter = adapterAssinates
                val alertDialog = AlertDialog.Builder(context!!)
                    .setTitle("Selecione um assinante")
                    .setView(view)
                    .setPositiveButton("Cadastrar") { dialog, which ->
                        val bundle = Bundle()
                        bundle.putString(ROTA_TAG, rota.id)
                        fragment_detalhes_rota_fab.findNavController().navigate(R.id.detalhes_rota_to_navigation_formulario_assinante, bundle)
                    }
                    .setNegativeButton("Cancelar") { dialog, which -> dialog.cancel() }
                    .show()

                viewModel.atualizaListaDeAssinantesSemRotas(adapterAssinates)
                adapterAssinates.clickListener = { assinante, _ ->
                    rota.assinantes.add(assinante)
                    viewModel.atualizaRota(rota)
                    alertDialog.dismiss()
                }
            } else {
                Toast.makeText(context, "So e possivel ter no maximo 9 assinantes por rota", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun alteraDescricao() {
        val builder = AlertDialog.Builder(context!!)
        builder.setTitle("Altera descricao da rota")
        val input = EditText(context)
        input.inputType = InputType.TYPE_CLASS_TEXT
        input.setText(rota.descricao)

        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        input.layoutParams = lp
        builder.setView(input)
        builder.setPositiveButton("Alterar") { dialog, which ->
            atualizaDescricao(input)
        }
        builder.setNegativeButton("Cancelar") { dialog, which -> dialog.cancel() }

        builder.show()
    }

    private fun atualizaDescricao(input: EditText) {
        rota.descricao = (input.text.toString())
        viewModel.atualizaRota(rota)
    }

    private fun configuraAdapter() {
        fragment_detalhes_rota_assinantes_recyclerView.adapter = adapter
        adapter.clickListener = { assinante, tipo ->
            when (tipo) {
                NORMAL -> abreFormularioPreenchido(assinante)
                DESVINCULAR -> desvinculaAssinante(assinante)
            }

        }
    }

    private fun desvinculaAssinante(assinante: Assinante) {
        val builder = AlertDialog.Builder(context!!)

        builder.apply {
            setTitle("Confirmação")
            setMessage("Esta assinante sera retirado dessa rota, deseja continuar?")
            setPositiveButton("Sim") { _, _ ->
                desvincular(assinante)
            }
            setNegativeButton("nao") { _, _ -> }
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun desvincular(assinante: Assinante) {
        rota.assinantes.toList().forEachIndexed { index, it ->
            if (assinante.id == it.id) {
                rota.assinantes.removeAt(index)
            }
        }
        assinante.rota = null
        viewModel.atualizaRota(rota)
        viewModel.atualizaAssinante(assinante)
    }

    private fun abreFormularioPreenchido(assinante: Assinante) {
        val bundle = Bundle()
        bundle.putParcelable(ASSINANTE_TAG, assinante)
        fragment_detalhes_rota_assinantes_recyclerView.findNavController().navigate(R.id.detalhes_rota_to_navigation_formulario_assinante, bundle)
    }

    private fun solicitaConfirmacaoParaExcluir() {
        val builder = AlertDialog.Builder(context!!)

        builder.apply {
            setTitle("Confirmação")
            setMessage("Esta rota será excluído, deseja continuar?")
            setPositiveButton("Sim") { _, _ ->
                deletar()
            }
            setNegativeButton("nao") { _, _ -> }
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun deletar() {
        viewModel.deletarRota(rota)
        activity?.onBackPressed()
    }
}