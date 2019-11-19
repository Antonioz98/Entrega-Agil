package com.antonio.entregaagil.ui.adapter.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.antonio.entregaagil.R
import com.antonio.entregaagil.modelo.Assinante
import com.antonio.entregaagil.ui.adapter.viewholder.DetalhesRotaViewHolder

class DetalhesRotaAdapter(private val context: Context) :
    RecyclerView.Adapter<DetalhesRotaViewHolder>() {

    private var assinantes: List<Assinante> = listOf()
    var clickListener: ((Assinante, Int) -> Unit) = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetalhesRotaViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_detalhe_rota, parent, false)

        return DetalhesRotaViewHolder(context, view)
    }

    override fun getItemCount(): Int {
        return assinantes.size
    }

    override fun onBindViewHolder(holder: DetalhesRotaViewHolder, position: Int) {
        holder.binView(assinantes[position], clickListener)
    }

    fun atualizaLista(assinantes: List<Assinante>) {
        this.assinantes = assinantes
        notifyDataSetChanged()
    }
}