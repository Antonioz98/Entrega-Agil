package com.antonio.entregaagil.ui.adapter.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.antonio.entregaagil.R
import com.antonio.entregaagil.modelo.Rota
import com.antonio.entregaagil.ui.adapter.viewholder.RotasViewHolder2

class RotasAdapter2(private val context: Context) : RecyclerView.Adapter<RotasViewHolder2>() {

    var rotas: List<Rota> = listOf()
    var clickListener: ((Rota, Int?) -> Unit) = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RotasViewHolder2 {
        val view = LayoutInflater.from(context).inflate(R.layout.item_rotas2, parent, false)

        return RotasViewHolder2(context, view)
    }

    override fun getItemCount(): Int {
        return rotas.size
    }

    override fun onBindViewHolder(holder: RotasViewHolder2, position: Int) {
        holder.binView(rotas[position], clickListener)
    }

    fun atualizaLista(rotas: List<Rota>) {
        this.rotas = rotas
        notifyDataSetChanged()
    }
}