package com.antonio.entregaagil.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.antonio.entregaagil.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.ext.android.inject

class LoginActivity : AppCompatActivity()
    , View.OnClickListener {

    private val auth: FirebaseAuth by inject()

    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        acitivity_login_cadastrar_textView.setOnClickListener(this)
        acitivity_login_bota_login.setOnClickListener(this)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        auth
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }


    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            startActivity(Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            finish()
        } else {
        }
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
        auth.signInWithEmailAndPassword(acitivity_login_input_email.text.toString(), acitivity_login_input_senha.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(
                        baseContext, getString(R.string.falha_login),
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }
}