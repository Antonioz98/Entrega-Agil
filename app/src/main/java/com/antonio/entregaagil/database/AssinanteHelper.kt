package com.antonio.entregaagil.database

import androidx.lifecycle.MutableLiveData
import com.antonio.entregaagil.modelo.Assinante

class AssinanteHelper(private val helper: FirebaseHelper) {

    private val assinantes = MutableLiveData<List<Assinante>?>()

    fun getAssinantesAtualizados(): MutableLiveData<List<Assinante>?> {
        atualizaAssinantes()
        return assinantes
    }

    fun setAssinante(assinante: Assinante) {
        helper.setAssinante(assinante
            , quandoFalha = {

            }, quandoSucesso = {

            })
    }

    fun deleteAssinante(assinante: Assinante) {
        helper.deleteAssinante(assinante
            , quandoFalha = {

            }, quandoSucesso = {

            })
    }

    private fun atualizaAssinantes() {
        helper.getAllAssinantes(
            quandoAtualiza = {
                val toObjects = it.toObjects(Assinante::class.java)
                assinantes.value = toObjects
            })
    }
}