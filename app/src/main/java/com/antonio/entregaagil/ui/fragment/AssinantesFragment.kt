package com.antonio.entregaagil.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearSmoothScroller
import com.antonio.entregaagil.R
import com.antonio.entregaagil.constante.ASSINANTE_TAG
import com.antonio.entregaagil.modelo.Assinante
import com.antonio.entregaagil.ui.adapter.list.AssinantesAdapter
import com.antonio.entregaagil.ui.viewmodel.AssinantesViewModel
import kotlinx.android.synthetic.main.fragment_assinantes.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class AssinantesFragment : Fragment() {

    private val adapter: AssinantesAdapter by inject()
    private val viewModel: AssinantesViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_assinantes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        confguraFAB()
        configuraAdapter()
        viewModel.mantemListaAtualiza(adapter)
    }

    private fun configuraAdapter() {
        fragment_assinantes_recyclerView.adapter = adapter
        adapter.clickListener = { assinante, posicao ->
            abreFormularioPreenchido(assinante, posicao)
        }
    }

    private fun abreFormularioPreenchido(assinante: Assinante, posicao: Int) {
        val bundle = Bundle()
        bundle.putParcelable(ASSINANTE_TAG, assinante)
        fragment_assinantes_recyclerView.findNavController().navigate(R.id.assinantes_to_formularioAssinanteFragment, bundle)
    }

    private fun confguraFAB() {
        fragment_assinantes_fab.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.assinantes_to_formularioAssinanteFragment))
    }

    private fun scrollToFinal() {
        val smoothScroller = object : LinearSmoothScroller(context) {
            override fun getVerticalSnapPreference(): Int {
                return SNAP_TO_START
            }
        }
        smoothScroller.targetPosition = adapter.itemCount
        fragment_assinantes_recyclerView.layoutManager?.startSmoothScroll(smoothScroller)
    }
}
