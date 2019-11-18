package com.antonio.entregaagil.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.antonio.entregaagil.R
import com.antonio.entregaagil.constante.ASSINANTE_TAG
import com.antonio.entregaagil.constante.ROTA_TAG
import com.antonio.entregaagil.extension.finalDoDia
import com.antonio.entregaagil.extension.formataDataResumida
import com.antonio.entregaagil.extension.inicioDoDia
import com.antonio.entregaagil.modelo.Assinante
import com.antonio.entregaagil.ui.dialog.PegarDataDialog
import com.antonio.entregaagil.ui.viewmodel.AssinantesViewModel
import kotlinx.android.synthetic.main.fragment_formulario_assinante.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class FormularioAssinanteFragment : Fragment() {


    private val viewModel: AssinantesViewModel by viewModel()
    private val DATE_PICKER_FRAGMENT = "PEGAR_DATA_FRAGMENT"
    private val dataInicio = Calendar.getInstance().inicioDoDia()
    private val dataFim = Calendar.getInstance().finalDoDia()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_formulario_assinante, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        activity?.title = "Formulario de assinante"

        preencheCampos()
        configurarDataFinal()
        configurarDataInicial()
        configuraBotaoSalvar()
    }

    private fun configuraBotaoSalvar() {
        fragment_formulario_assinante_salvar.setOnClickListener {
            if (camposValidos()) {
                val assinante: Assinante

                if (arguments?.containsKey(ASSINANTE_TAG) == true) {
                    assinante = arguments?.getParcelable<Assinante>(ASSINANTE_TAG)!!
                } else {
                    assinante = Assinante()
                }

                assinante.nome = fragment_formulario_assinante_nome.text.toString()
                assinante.endereco = fragment_formulario_assinante_endereco.text.toString()
                assinante.numero = fragment_formulario_assinante_numero.text.toString().toInt()
                assinante.bairro = fragment_formulario_assinante_bairro.text.toString()
                assinante.complemento = fragment_formulario_assinante_complemento.text.toString()
                assinante.inicioAssinatura = dataInicio.time
                assinante.fimAssinatura = dataFim.time

                salvar(assinante)
            } else {

            }
        }
    }

    private fun camposValidos(): Boolean {
        return fragment_formulario_assinante_nome.text.toString().isNotEmpty() &&
                fragment_formulario_assinante_endereco.text.toString().isNotEmpty() &&
                fragment_formulario_assinante_numero.text.toString().isNotEmpty() &&
                fragment_formulario_assinante_inicio_assinatura.text.toString().isNotEmpty() &&
                fragment_formulario_assinante_termino_assinatura.text.toString().isNotEmpty() &&
                fragment_formulario_assinante_bairro.text.toString().isNotEmpty()
    }

    private fun salvar(assinante: Assinante) {
        arguments?.getString(ROTA_TAG, null)?.let {
            assinante.rota = it
        }
        viewModel.cadastrarAssinante(assinante)
        activity?.onBackPressed()
    }

    private fun preencheCampos() {
        val assinante = arguments?.getParcelable<Assinante>(ASSINANTE_TAG)
        assinante?.apply {
            fragment_formulario_assinante_nome.setText(nome)
            fragment_formulario_assinante_endereco.setText(endereco)
            fragment_formulario_assinante_numero.setText(numero.toString())
            fragment_formulario_assinante_bairro.setText(bairro)
            fragment_formulario_assinante_inicio_assinatura.setText(
                inicioAssinatura?.formataDataResumida() ?: dataInicio.time.formataDataResumida()
            )
            fragment_formulario_assinante_termino_assinatura.setText(
                fimAssinatura?.formataDataResumida() ?: dataFim.time.formataDataResumida()
            )
            fragment_formulario_assinante_complemento.setText(complemento)
        }
    }

    private fun configurarDataInicial() {
        fragment_formulario_assinante_inicio_assinatura.setOnClickListener {
            PegarDataDialog { _, ano, mes, dia ->
                dataInicio.set(ano, mes, dia)
                fragment_formulario_assinante_inicio_assinatura.setText(dataInicio.time.formataDataResumida())
            }.show(fragmentManager!!, DATE_PICKER_FRAGMENT)

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val assinante = arguments?.getParcelable<Assinante>(ASSINANTE_TAG)
        if (assinante != null) {
            inflater.inflate(R.menu.menu_formulario_assinante, menu)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_deletar -> solicitaConfirmacaoParaExcluir()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun solicitaConfirmacaoParaExcluir() {
        val builder = AlertDialog.Builder(context!!)

        builder.apply {
            setTitle("Confirmação")
            setMessage("Este assinante será excluído, deseja continuar?")
            setPositiveButton("Sim") { _, _ ->
                deletar()
            }
            setNegativeButton("nao") { _, _ -> }
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun deletar() {
        val assinante = arguments?.getParcelable<Assinante>(ASSINANTE_TAG)
        viewModel.deletaAssinantes(assinante!!)
        activity?.onBackPressed()
    }

    private fun configurarDataFinal() {
        fragment_formulario_assinante_termino_assinatura.setOnClickListener {
            PegarDataDialog { _, ano, mes, dia ->
                dataFim.set(ano, mes, dia)
                fragment_formulario_assinante_termino_assinatura.setText(dataFim.time.formataDataResumida())
            }.show(fragmentManager!!, DATE_PICKER_FRAGMENT)
        }
    }
}