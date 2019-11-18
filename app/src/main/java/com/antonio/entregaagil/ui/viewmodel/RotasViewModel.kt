package com.antonio.entregaagil.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.antonio.entregaagil.database.AssinanteHelper
import com.antonio.entregaagil.database.RotaHelper
import com.antonio.entregaagil.modelo.Assinante
import com.antonio.entregaagil.modelo.Rota
import com.antonio.entregaagil.ui.adapter.list.AssinantesAdapter
import com.antonio.entregaagil.ui.adapter.list.AssinantesAdapter2
import com.antonio.entregaagil.ui.adapter.list.RotasAdapter

class RotasViewModel(private val rotaHelper: RotaHelper, private val assinanteHelper: AssinanteHelper) : ViewModel() {

    fun atualizaListaDeRotas(adapter: RotasAdapter) {
        rotaHelper.getRotasAtualizadas().observeForever{
            it?.let {
                adapter.atualizaLista(it)
            }
        }
    }

    fun atualizaRota(rota: Rota) {
        rotaHelper.setRota(rota)

    }

    fun deletarRota(rota: Rota) {
        rotaHelper.deleteRota(rota)
    }

    fun getRota(rota: Rota): MutableLiveData<Rota?> {
        return rotaHelper.getRota(rota)
    }

    fun atualizaListaDeAssinantesSemRotas(adapter: AssinantesAdapter2) {
        rotaHelper.getAssinantesSemRota().observeForever {
            it?.let { it1 -> adapter.atualizaLista(it1) }
        }
    }

    fun atualizaAssinante(assinante: Assinante) {
        assinanteHelper.setAssinante(assinante)
    }

    fun getRotas(): MutableLiveData<List<Rota>?> {
        return rotaHelper.getRotasAtualizadas()
    }
}
