package com.antonio.entregaagil.database

import androidx.lifecycle.MutableLiveData
import com.antonio.entregaagil.modelo.Assinante
import com.antonio.entregaagil.modelo.Rota

class RotaHelper(private val helper: FirebaseHelper) {

    private val assinantes = MutableLiveData<List<Rota>?>()

    fun getRotasAtualizadas(): MutableLiveData<List<Rota>?> {
        atualizaRotas()
        return assinantes
    }

    fun getAssinantesSemRota(): MutableLiveData<List<Assinante>?> {
        val data = MutableLiveData<List<Assinante>?>()
        helper.getAssinantesSemRota(
            quandoAtualiza = {
                val toObjects = it.toObjects(Assinante::class.java)
                data.value = toObjects
            })
        return data
    }

    fun setRota(rota: Rota) {
        helper.setRota(rota
            , quandoFalha = {

            }, quandoSucesso = {
                for (assinante in rota.assinantes) {
                    assinante.rota = rota.id
                    helper.setAssinante(assinante, {}, {})
                }
            })
    }

    fun deleteRota(rota: Rota) {
        helper.deleteRota(rota
            , quandoFalha = {

            }, quandoSucesso = {
                for (assinante in rota.assinantes) {
                    assinante.rota = null
                    helper.setAssinante(assinante, {}, {})
                }
            })
    }

    private fun atualizaRotas() {
        helper.getAllRotas(
            quandoAtualiza = {
                val toObjects = it.toObjects(Rota::class.java)
                assinantes.value = toObjects as List<Rota>
            })
    }

    fun getRota(rota: Rota) : MutableLiveData<Rota?>{
        val mutableLiveData = MutableLiveData<Rota?>()
        helper.getOneRota(rota.id,
            quandoAtualiza = {
                val toObjects = it.toObject(Rota::class.java)
                mutableLiveData.value = toObjects
            })
        return  mutableLiveData
    }
}