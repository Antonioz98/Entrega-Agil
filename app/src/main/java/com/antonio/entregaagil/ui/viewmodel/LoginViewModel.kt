package com.antonio.entregaagil.ui.viewmodel

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class LoginViewModel(private val auth: FirebaseAuth) : ViewModel() {

    fun login(activity: Activity, email: String, senha: String): MutableLiveData<Task<AuthResult>?> {
        val data = MutableLiveData<Task<AuthResult>?>()
        auth.signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener(activity) { task ->
                data.value = task
            }
        return data
    }

    fun cadastrar(activity: Activity, email: String, senha: String, usuario: String): MutableLiveData<Task<AuthResult>?> {
        val data = MutableLiveData<Task<AuthResult>?>()
        auth.createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    val build = UserProfileChangeRequest.Builder()
                        .setDisplayName(usuario)
                        .build()

                    auth.currentUser?.updateProfile(build)?.addOnCompleteListener {
                        data.value = task
                    }
                } else {
                    data.value = task
                }
            }
        return data
    }
}