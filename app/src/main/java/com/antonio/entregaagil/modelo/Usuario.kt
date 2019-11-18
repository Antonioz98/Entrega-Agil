package com.antonio.entregaagil.modelo

import com.google.firebase.auth.FirebaseUser

import java.io.Serializable

class Usuario : Serializable {

    var uid: String? = null
    var displayName: String? = null
    var email: String? = null
    var photoUrl: String? = null
    var phoneNumber: String? = null

    companion object {

        fun newUser(user: FirebaseUser?): Usuario {
            val usuario = Usuario()
            usuario.uid = user?.uid
            usuario.displayName = user?.displayName
            usuario.email = user?.email
            usuario.phoneNumber = user?.phoneNumber
            if (user?.photoUrl != null)
                usuario.photoUrl = user.photoUrl.toString()
            else
                usuario.photoUrl = null
            return usuario
        }
    }
}