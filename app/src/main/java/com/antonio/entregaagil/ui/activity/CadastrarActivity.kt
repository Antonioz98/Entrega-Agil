package com.antonio.entregaagil.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.antonio.entregaagil.R
import com.antonio.entregaagil.ui.viewmodel.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.activity_cadastrar.*
import org.koin.android.viewmodel.ext.android.viewModel

class CadastrarActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastrar)
        auth = FirebaseAuth.getInstance()

        acitivity_cadastrar_botao_cadastrar.setOnClickListener { cadastrarConta() }
        acitivity_cadastrar_logar_textView.setOnClickListener { abrirTelaDeLogin() }
    }

    private fun abrirTelaDeLogin() {
        startActivity(Intent(this, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        finish()
    }

    private fun abrirTelaInicial() {
        startActivity(Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        finish()
    }

    private fun cadastrarConta() {
        if (camposValidos()) {
            activity_cadastrar_login_progress_bar.visibility = VISIBLE
            acitivity_cadastrar_botao_cadastrar.isEnabled = false
            viewModel.cadastrar(
                this,
                acitivity_cadastrar_input_email.text.toString(),
                acitivity_cadastrar_input_senha.text.toString(),
                acitivity_cadastrar_input_nome.text.toString()
            ).observe(this, Observer {
                it?.apply {
                    acitivity_cadastrar_botao_cadastrar.isEnabled = true
                    activity_cadastrar_login_progress_bar.visibility = GONE
                    if (isSuccessful) {
                        abrirTelaInicial()
                    } else {
                        Toast.makeText(baseContext, getString(R.string.erro_cadastrar), Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

    private fun camposValidos(): Boolean {
        return when {
            acitivity_cadastrar_input_nome.text.isNullOrEmpty() -> {
                acitivity_cadastrar_layout_nome.error = getString(R.string.erro_edit_text_nome)
                false
            }
            acitivity_cadastrar_input_email.text.isNullOrEmpty() -> {
                acitivity_cadastrar_layout_nome.isErrorEnabled = false
                acitivity_cadastrar_layout_email.error = getString(R.string.erro_edit_text_email)
                false
            }
            acitivity_cadastrar_input_senha.text.isNullOrEmpty() -> {
                acitivity_cadastrar_layout_nome.isErrorEnabled = false
                acitivity_cadastrar_layout_email.isErrorEnabled = false
                acitivity_cadastrar_layout_senha.error = getString(R.string.erro_edit_text_senha)
                false
            }
            else -> {

                acitivity_cadastrar_layout_nome.isErrorEnabled = false
                acitivity_cadastrar_layout_email.isErrorEnabled = false
                acitivity_cadastrar_layout_senha.isErrorEnabled = false
                true
            }
        }
    }
}