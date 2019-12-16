package com.antonio.entregaagil.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
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
    private var dataInicio: Calendar? = null
    private var dataFim: Calendar? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_formulario_assinante, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.show()
        activity?.title = getString(R.string.formulario_assinante)

        preencheCampos()
        configurarDataFinal()
        configurarDataInicial()
        configuraBotaoSalvar()
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as AppCompatActivity).supportActionBar?.hide()
    }


    private fun configuraBotaoSalvar() {
        fragment_formulario_assinante_salvar.setOnClickListener {
            if (camposValidos()) {
                val assinante: Assinante = if (arguments?.containsKey(ASSINANTE_TAG) == true) {
                    arguments?.getParcelable(ASSINANTE_TAG)!!
                } else {
                    Assinante()
                }

                assinante.nome = fragment_formulario_assinante_nome.text.toString()
                assinante.endereco = fragment_formulario_assinante_endereco.text.toString()
                assinante.numero = fragment_formulario_assinante_numero.text.toString().toInt()
                assinante.bairro = fragment_formulario_assinante_bairro.text.toString()
                assinante.complemento = fragment_formulario_assinante_complemento.text.toString()
                assinante.inicioAssinatura = if (dataInicio != null) dataInicio!!.time else null
                assinante.fimAssinatura = if (dataFim != null) dataFim!!.time else null

                salvar(assinante)
            } else {

            }
        }
    }

    private fun camposValidos(): Boolean {
        return when {
            fragment_formulario_assinante_nome.text.isNullOrEmpty() -> {
                fragment_formulario_assinante_nome_layout.error = getString(R.string.campo_obrigatorio)
                false
            }
            fragment_formulario_assinante_endereco.text.isNullOrEmpty() -> {
                fragment_formulario_assinante_nome_layout.isErrorEnabled = false
                fragment_formulario_assinante_endereco_layout.error = getString(R.string.campo_obrigatorio)
                false
            }
            fragment_formulario_assinante_numero.text.isNullOrEmpty() -> {
                fragment_formulario_assinante_nome_layout.isErrorEnabled = false
                fragment_formulario_assinante_endereco_layout.isErrorEnabled = false
                fragment_formulario_assinante_numero_layout.error = getString(R.string.campo_obrigatorio)
                false
            }
            fragment_formulario_assinante_bairro.text.isNullOrEmpty() -> {
                fragment_formulario_assinante_nome_layout.isErrorEnabled = false
                fragment_formulario_assinante_endereco_layout.isErrorEnabled = false
                fragment_formulario_assinante_numero_layout.isErrorEnabled = false
                fragment_formulario_assinante_bairro_layout.error = getString(R.string.campo_obrigatorio)
                false
            }
            else -> {

                fragment_formulario_assinante_nome_layout.isErrorEnabled = false
                fragment_formulario_assinante_endereco_layout.isErrorEnabled = false
                fragment_formulario_assinante_numero_layout.isErrorEnabled = false
                fragment_formulario_assinante_bairro_layout.isErrorEnabled = false
                true
            }
        }
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
            inicioAssinatura?.let {
                dataInicio = Calendar.getInstance().inicioDoDia()
                dataInicio!!.time = it
                fragment_formulario_assinante_inicio_assinatura.setText(it.formataDataResumida())
            }
            fimAssinatura?.let {
                dataFim = Calendar.getInstance().finalDoDia()
                dataFim!!.time = it
                fragment_formulario_assinante_termino_assinatura.setText(it.formataDataResumida())
            }
            fragment_formulario_assinante_complemento.setText(complemento)
        }
    }

    private fun configurarDataInicial() {
        fragment_formulario_assinante_inicio_assinatura.setOnClickListener {
            PegarDataDialog { _, ano, mes, dia ->
                if (dataInicio == null) {
                    dataInicio = Calendar.getInstance().inicioDoDia()
                }
                dataInicio!!.set(ano, mes, dia)
                fragment_formulario_assinante_inicio_assinatura.setText(dataInicio!!.time.formataDataResumida())
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
            android.R.id.home -> activity?.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun solicitaConfirmacaoParaExcluir() {
        val builder = AlertDialog.Builder(context!!)

        builder.apply {
            setTitle(getString(R.string.confirmacao))
            setMessage(getString(R.string.confirmacao_exclusao_assinante))
            setPositiveButton(getString(R.string.sim)) { _, _ ->
                deletar()
            }
            setNegativeButton(getString(R.string.nao)) { _, _ -> }
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
                if (dataFim == null) {
                    dataFim = Calendar.getInstance().finalDoDia()
                }
                dataFim!!.set(ano, mes, dia)
                fragment_formulario_assinante_termino_assinatura.setText(dataFim!!.time.formataDataResumida())
            }.show(fragmentManager!!, DATE_PICKER_FRAGMENT)
        }
    }
}