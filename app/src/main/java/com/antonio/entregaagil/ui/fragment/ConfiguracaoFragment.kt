package com.antonio.entregaagil.ui.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.antonio.entregaagil.R
import com.antonio.entregaagil.modelo.Usuario
import com.antonio.entregaagil.ui.activity.LoginActivity
import com.antonio.entregaagil.ui.viewmodel.AssinantesViewModel
import com.antonio.entregaagil.ui.viewmodel.RotasViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_configuracao.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class ConfiguracaoFragment : Fragment() {

    private val auth: FirebaseAuth by inject()
    private val usuario: Usuario by inject()
    private val assinanteViewModel: AssinantesViewModel by viewModel()
    private val rotaViewModel: RotasViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_configuracao, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rotaViewModel.getRotas().observe(this, Observer {
            configuracao_quantidade_rota.text = it?.size.toString()
        })
        assinanteViewModel.getAssinantes().observe(this, Observer {
            it?.let { assinantes ->
                var cont = 0
                for (assinante in assinantes) {
                    if (assinante.rota == null) {
                        cont++
                    }
                }
                configuracao_quantidade_assinantes_sem_rota.text = cont.toString()
                configuracao_quantidade_assinantes.text = assinantes.size.toString()
            }
        })
        configuracao_nome.text = usuario.displayName
        configuracao_email.text = usuario.email

        configuracao_sair.setOnClickListener {
            auth.signOut()
            startActivity(Intent(context, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            activity?.finish()
        }
    }

}
