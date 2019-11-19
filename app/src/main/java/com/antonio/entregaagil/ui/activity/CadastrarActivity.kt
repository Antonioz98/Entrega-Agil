package com.antonio.entregaagil.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.antonio.entregaagil.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.activity_cadastrar.*

class CadastrarActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

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
        auth.createUserWithEmailAndPassword(acitivity_cadastrar_input_email.text.toString(), acitivity_cadastrar_input_senha.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val build = UserProfileChangeRequest.Builder()
                        .setDisplayName(acitivity_cadastrar_input_nome.text.toString())
                        .build()

                    auth.currentUser?.updateProfile(build)?.addOnCompleteListener {
                        abrirTelaInicial()
                    }
                } else {
                    Toast.makeText(baseContext, getString(R.string.erro_cadastrar), Toast.LENGTH_SHORT).show()
                }

            }
    }

}