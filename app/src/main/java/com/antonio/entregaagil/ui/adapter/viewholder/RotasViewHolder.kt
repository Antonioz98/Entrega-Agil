package com.antonio.entregaagil.ui.adapter.viewholder

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.antonio.entregaagil.modelo.Rota
import kotlinx.android.synthetic.main.item_rotas.view.*

class RotasViewHolder(private val context: Context, itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun binView(rota: Rota, clickListener: ((Rota, Int) -> Unit)) {
        itemView.item_rota_descricao.text = rota.descricao
        itemView.item_rota_quantidade.text = "Cadastrados: ${rota.assinantes.size}/9"
        itemView.setOnClickListener { clickListener(rota, adapterPosition) }
    }
}
