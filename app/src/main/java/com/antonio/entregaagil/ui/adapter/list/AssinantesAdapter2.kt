package com.antonio.entregaagil.ui.adapter.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.antonio.entregaagil.R
import com.antonio.entregaagil.modelo.Assinante
import com.antonio.entregaagil.ui.adapter.viewholder.AssinantesViewHolder2
import java.util.*

class AssinantesAdapter2(private val context: Context) : RecyclerView.Adapter<AssinantesViewHolder2>() {

    private val assinantes: MutableList<Assinante> = mutableListOf()
    private var arraylist: List<Assinante> = listOf()
    var clickListener: ((Assinante, Int) -> Unit) = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssinantesViewHolder2 {
        val view = LayoutInflater.from(context).inflate(R.layout.item_assinante2, parent, false)

        return AssinantesViewHolder2(context, view)
    }

    override fun getItemCount(): Int {
        return assinantes.size
    }

    override fun onBindViewHolder(holder: AssinantesViewHolder2, position: Int) {
        holder.binView(assinantes[position], clickListener)
    }

    fun atualizaLista(assinantes: List<Assinante>) {
        this.assinantes.clear()
        this.assinantes.addAll(assinantes)
        this.arraylist = assinantes
        notifyDataSetChanged()
    }

    fun filter(filtrar: String) {
        val charText = filtrar.toLowerCase(Locale.getDefault())
        assinantes.clear()
        if (charText.isEmpty()) {
            assinantes.addAll(arraylist)
        } else {
            for (wp in arraylist) {
                when {
                    wp.nome.toLowerCase(Locale.getDefault()).contains(charText) -> assinantes.add(wp)
                    wp.bairro.toLowerCase(Locale.getDefault()).contains(charText) -> assinantes.add(wp)
                    wp.endereco.toLowerCase(Locale.getDefault()).contains(charText) -> assinantes.add(wp)
                }
            }
        }
        notifyDataSetChanged()
    }
}