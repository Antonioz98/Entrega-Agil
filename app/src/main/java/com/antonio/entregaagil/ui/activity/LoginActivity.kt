package com.antonio.entregaagil.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.antonio.entregaagil.R
import com.antonio.entregaagil.ui.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        acitivity_login_cadastrar_textView.setOnClickListener(this)
        acitivity_login_bota_login.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.acitivity_login_bota_login -> logar()
            R.id.acitivity_login_cadastrar_textView -> {
                startActivity(Intent(this, CadastrarActivity::class.java))
                finish()
            }
        }
    }

    private fun logar() {
        if (acitivity_login_input_email.text.isNullOrEmpty()) {
            acitivity_login_layout_email.error = getString(R.string.erro_edit_text_email)
        } else {
            acitivity_login_layout_email.isErrorEnabled = false
            if (acitivity_login_input_senha.text.isNullOrEmpty()) {
                acitivity_login_layout_senha.error = getString(R.string.erro_edit_text_senha)
            } else {
                acitivity_login_layout_senha.isErrorEnabled = false
                activity_login_progress_bar.visibility = VISIBLE
                acitivity_login_bota_login.isEnabled = false
                viewModel.login(this, acitivity_login_input_email.text.toString(), acitivity_login_input_senha.text.toString())
                    .observe(this, Observer {
                        it?.let {
                            activity_login_progress_bar.visibility = GONE
                            acitivity_login_bota_login.isEnabled = true
                            if (it.isSuccessful) {
                                startActivity(Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                finish()
                            } else {
                                Toast.makeText(baseContext, getString(R.string.falha_login), Toast.LENGTH_SHORT).show()
                                acitivity_login_layout_email.error = getString(R.string.erro_verique_informacoes)
                                acitivity_login_layout_senha.error = getString(R.string.erro_verique_informacoes)
                            }
                        }
                    })
            }
        }
    }
}