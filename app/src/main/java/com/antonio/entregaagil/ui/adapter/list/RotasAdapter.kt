package com.antonio.entregaagil.ui.adapter.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.antonio.entregaagil.R
import com.antonio.entregaagil.modelo.Rota
import com.antonio.entregaagil.ui.adapter.viewholder.RotasViewHolder

class RotasAdapter(private val context: Context) : RecyclerView.Adapter<RotasViewHolder>() {

    private var rotas: List<Rota> = listOf()
    var clickListener: ((Rota, Int) -> Unit) = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RotasViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_rotas, parent, false)

        return RotasViewHolder(context, view)
    }

    override fun getItemCount(): Int {
        return rotas.size
    }

    override fun onBindViewHolder(holder: RotasViewHolder, position: Int) {
        holder.binView(rotas[position], clickListener)
    }

    fun atualizaLista(rotas: List<Rota>) {
        this.rotas = rotas
        notifyDataSetChanged()
    }

    fun altera(assinante: Rota?, posicao: Int?) {
        if (assinante != null && posicao != null) {
            val toMutableList = rotas.toMutableList()
            toMutableList[posicao] = assinante
            this.rotas = toMutableList
            notifyItemChanged(posicao)
        }
    }

    fun exclui(posicao: Int?) {
        if (posicao != null) {
            val toMutableList = rotas.toMutableList()
            toMutableList.removeAt(posicao)
            this.rotas = toMutableList
            notifyItemRemoved(posicao)
        }
    }

    fun adiciona(assinante: Rota?) {
        if (assinante != null) {
            val toMutableList = rotas.toMutableList()
            toMutableList.add(assinante)
            this.rotas = toMutableList
            notifyItemInserted(this.rotas.size)
        }
    }
}