package com.antonio.entregaagil.database

import com.antonio.entregaagil.constante.FIREBASE_ASSINANTES
import com.antonio.entregaagil.constante.FIREBASE_ROTAS
import com.antonio.entregaagil.constante.FIREBASE_USER
import com.antonio.entregaagil.modelo.Assinante
import com.antonio.entregaagil.modelo.Rota
import com.antonio.entregaagil.modelo.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

class FirebaseHelper(private val db: FirebaseFirestore) {

    init {
        val settings = FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build()
        db.firestoreSettings = settings
        val newUser = Usuario.newUser(FirebaseAuth.getInstance().currentUser)
        getUser().set(newUser)
    }

    fun getAllAssinantes(quandoAtualiza: (QuerySnapshot) -> Unit) {
        getUser().collection(FIREBASE_ASSINANTES)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                snapshot?.let {
                    quandoAtualiza(it)
                }
            }
    }

    fun getAllRotas(quandoAtualiza: (QuerySnapshot) -> Unit) {
        getUser().collection(FIREBASE_ROTAS)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                snapshot?.let {
                    quandoAtualiza(it)
                }
            }
    }

    fun getOneRota(rotaId: String, quandoAtualiza: (DocumentSnapshot) -> Unit) {
        getDocument(FIREBASE_ROTAS, rotaId)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                snapshot?.let {
                    quandoAtualiza(it)
                }
            }
    }

    fun deleteRota(rota: Rota, quandoFalha: () -> Unit, quandoSucesso: () -> Unit) {
        getDocument(FIREBASE_ROTAS, rota.id).delete()
            .addOnFailureListener { quandoFalha() }
            .addOnSuccessListener {
                quandoSucesso()
                for (assinante in rota.assinantes) {
                    assinante.rota = null
                    setAssinante(assinante, {}, {})
                }
            }
    }

    fun deleteAssinante(assinante: Assinante, quandoFalha: () -> Unit, quandoSucesso: () -> Unit) {
        getDocument(FIREBASE_ASSINANTES, assinante.id).delete()
            .addOnFailureListener { quandoFalha() }
            .addOnSuccessListener {
                quandoSucesso()
                assinante.rota?.let { it1 ->
                    getOneRota(it1) {
                        val rota = it.toObject(Rota::class.java)
                        rota?.assinantes?.toList()?.forEachIndexed { index, ass ->
                            if (ass.id == assinante.id) {
                                rota.assinantes.removeAt(index)
                                setRota(rota, {}, {})
                                return@forEachIndexed
                            }
                        }
                    }
                }
            }
    }

    fun setRota(rota: Rota, quandoFalha: () -> Unit, quandoSucesso: () -> Unit) {
        getDocument(FIREBASE_ROTAS, rota.id).set(rota)
            .addOnFailureListener { quandoFalha() }
            .addOnSuccessListener { quandoSucesso() }
    }

    fun setAssinante(assinante: Assinante, quandoFalha: () -> Unit, quandoSucesso: () -> Unit) {
        getDocument(FIREBASE_ASSINANTES, assinante.id).set(assinante)
            .addOnFailureListener {
                quandoFalha()
            }
            .addOnSuccessListener { _ ->
                quandoSucesso()
                assinante.rota?.let { rota ->
                    getDocument(FIREBASE_ROTAS, rota).get().addOnSuccessListener {ds ->
                        ds.toObject(Rota::class.java)?.let {
                            it.assinantes.toList().forEachIndexed { index, ass ->
                                if (ass.id == assinante.id) {
                                    it.assinantes[index] = assinante
                                    setRota(it, {}, {})
                                    return@addOnSuccessListener
                                }
                            }
                            it.assinantes.add(assinante)
                            setRota(it, {}, {})
                        }
                    }
                }
            }
    }

    private fun getDocument(collection: String, id: String): DocumentReference {
        return getUser().collection(collection).document(id)
    }

    private fun getUser(): DocumentReference {
        val uidUser = FirebaseAuth.getInstance().currentUser!!.uid
        return db.collection(FIREBASE_USER).document(uidUser)
    }

    fun getAssinantesSemRota(quandoAtualiza: (QuerySnapshot) -> Unit) {
        getUser().collection(FIREBASE_ASSINANTES)
            .whereEqualTo("rota", null)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                snapshot?.let {
                    quandoAtualiza(it)
                }
            }
    }
}
