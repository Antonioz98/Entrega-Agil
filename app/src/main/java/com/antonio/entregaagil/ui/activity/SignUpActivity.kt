package com.antonio.entregaagil.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.antonio.entregaagil.R
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.activity_up_sign.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_up_sign)
        auth = FirebaseAuth.getInstance()

        btnFirebaseSignUp.setOnClickListener { cadastrarConta() }
        tvToSignIn.setOnClickListener { abrirTelaDeLogin() }
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
        auth.createUserWithEmailAndPassword(titEmail.text.toString(), titPassword.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val build = UserProfileChangeRequest.Builder()
                        .setDisplayName(titName.text.toString())
                        .build()

                    auth.currentUser?.updateProfile(build)?.addOnCompleteListener {
                        abrirTelaInicial()
                    }
                } else {
                    Toast.makeText(baseContext, "Nao foi possivel cadastrar o usuario.", Toast.LENGTH_SHORT).show()
                }

            }
    }

}