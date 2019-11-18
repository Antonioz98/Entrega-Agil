package com.antonio.entregaagil.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.antonio.entregaagil.database.AssinanteHelper
import com.antonio.entregaagil.modelo.Assinante
import com.antonio.entregaagil.ui.adapter.list.AssinantesAdapter

class AssinantesViewModel(private val assinanteHelper: AssinanteHelper) : ViewModel() {

    fun cadastrarAssinante(assinante: Assinante) {
        assinanteHelper.setAssinante(assinante)
    }

    fun mantemListaAtualiza(adapter: AssinantesAdapter) {
        assinanteHelper.getAssinantesAtualizados().observeForever {
            it?.let { it1 -> adapter.atualizaLista(it1) }
        }
    }

    fun getAssinantes(): MutableLiveData<List<Assinante>?> {
        return assinanteHelper.getAssinantesAtualizados()
    }


    fun deletaAssinantes(assinante: Assinante) {
        assinanteHelper.deleteAssinante(assinante)
    }

}
