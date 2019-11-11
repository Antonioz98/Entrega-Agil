package com.antonio.entregaagil.ui.fragment

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.antonio.entregaagil.R
import com.antonio.entregaagil.constante.ASSINANTES_POR_ROTA
import com.antonio.entregaagil.constante.ASSINANTE_TAG
import com.antonio.entregaagil.constante.ROTA_TAG
import com.antonio.entregaagil.modelo.Assinante
import com.antonio.entregaagil.modelo.Rota
import com.antonio.entregaagil.ui.adapter.list.DetalhesRotaAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_detalhes_rota.*
import org.koin.android.ext.android.inject

class DetalhesRotaFragment : Fragment() {

    private val adapter: DetalhesRotaAdapter by inject()
    private var rota = Rota()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detalhes_rota, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.hide()

        rota = arguments?.getParcelable(ROTA_TAG)!!
        fragment_detalhes_rota_titulo.text = rota.descricao
        fragment_detalhes_rota_quantida.text = "Assinantes: ${rota.assinantes.size}/$ASSINANTES_POR_ROTA"

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

    override fun onDestroy() {
        super.onDestroy()
        (activity as AppCompatActivity).supportActionBar?.show()
    }

    private fun confguraFab() {
        fragment_detalhes_rota_fab.setOnClickListener {
//            if (adapter.itemCount >= ASSINANTES_POR_ROTA) {
//                Snackbar.make(fragment_detalhes_rota_fab, "O limite de cadastro por rota e de $ASSINANTES_POR_ROTA", Snackbar.LENGTH_SHORT).show()
//            } else {
                fragment_detalhes_rota_fab.findNavController().navigate(R.id.detalhes_rota_to_navigation_formulario_assinante)
//            }
        }
    }

    private fun alteraDescricao() {
        val builder = AlertDialog.Builder(context!!)
        builder.setTitle("Altera descricao da rota")
        val input = EditText(context)
        input.inputType = InputType.TYPE_CLASS_TEXT
        input.setText(rota.descricao)

        val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        input.layoutParams = lp
        builder.setView(input)
        builder.setPositiveButton("Alterar") { dialog, which ->
            rota.descricao = (input.text.toString())
        }
        builder.setNegativeButton("Cancelar") { dialog, which -> dialog.cancel() }

        builder.show()
    }

    private fun configuraAdapter() {
        fragment_detalhes_rota_assinantes_recyclerView.adapter = adapter
        val assinantes = rota.assinantes
        adapter.atualizaLista(assinantes)
        adapter.clickListener = { assinante, posicao ->
            abreFormularioPreenchido(assinante)
        }
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
        activity?.onBackPressed()
    }
}