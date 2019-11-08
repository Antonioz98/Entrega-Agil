package com.antonio.entregaagil.ui.adapter.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.antonio.entregaagil.R
import com.antonio.entregaagil.modelo.Assinante
import com.antonio.entregaagil.ui.adapter.viewholder.AssinantesViewHolder

class AssinantesAdapter(private val context: Context) :
    RecyclerView.Adapter<AssinantesViewHolder>() {

    private var assinantes: List<Assinante> = listOf()
    var clickListener: ((Assinante, Int) -> Unit) = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssinantesViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_assinante, parent, false)

        return AssinantesViewHolder(context, view)
    }

    override fun getItemCount(): Int {
        return assinantes.size
    }

    override fun onBindViewHolder(holder: AssinantesViewHolder, position: Int) {
        holder.binView(assinantes[position], clickListener)
    }

    fun atualizaLista(assinantes: List<Assinante>) {
        this.assinantes = assinantes
        notifyDataSetChanged()
    }

    fun altera(assinante: Assinante?, posicao: Int?) {
        if (assinante != null && posicao != null) {
            val toMutableList = assinantes.toMutableList()
            toMutableList[posicao] = assinante
            this.assinantes = toMutableList
            notifyItemChanged(posicao)
        }
    }

    fun exclui(posicao: Int?) {
        if (posicao != null) {
            val toMutableList = assinantes.toMutableList()
            toMutableList.removeAt(posicao)
            this.assinantes = toMutableList
            notifyItemRemoved(posicao)
        }
    }

    fun adiciona(assinante: Assinante?) {
        if (assinante != null) {
            val toMutableList = assinantes.toMutableList()
            toMutableList.add(assinante)
            this.assinantes = toMutableList
            notifyItemInserted(this.assinantes.size)
        }
    }
}