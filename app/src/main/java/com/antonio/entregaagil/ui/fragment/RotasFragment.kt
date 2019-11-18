package com.antonio.entregaagil.ui.fragment

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearSmoothScroller
import com.antonio.entregaagil.R
import com.antonio.entregaagil.constante.ROTA_TAG
import com.antonio.entregaagil.modelo.Rota
import com.antonio.entregaagil.ui.adapter.list.RotasAdapter
import com.antonio.entregaagil.ui.viewmodel.RotasViewModel
import kotlinx.android.synthetic.main.fragment_rotas.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

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
        viewModel.atualizaListaDeRotas(adapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        (activity as AppCompatActivity).supportActionBar?.show()
    }

    private fun configuraAdapter() {
        fragment_rotas_recyclerView.adapter = adapter
        adapter.clickListener = { rota, posicao ->
            abreDetalheDaRota(rota)
        }
    }

    private fun abreDetalheDaRota(rota: Rota) {
        val bundle = Bundle()
        bundle.putParcelable(ROTA_TAG, rota)
        fragment_rotas_recyclerView.findNavController().navigate(R.id.rotas_to_navigation_detalhes_rota, bundle)
    }

    private fun confguraFAB() {
        fragment_rotas_fab.setOnClickListener {
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


    private fun cadastraRota(descricao: String) {
        val rota = Rota()
        rota.descricao = descricao
        viewModel.atualizaRota(rota)
    }

    private fun scrollToFinal() {
        val smoothScroller = object : LinearSmoothScroller(context) {
            override fun getVerticalSnapPreference(): Int {
                return SNAP_TO_START
            }
        }
        smoothScroller.targetPosition = adapter.itemCount
        fragment_rotas_recyclerView.layoutManager?.startSmoothScroll(smoothScroller)
    }
}
