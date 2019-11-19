package com.antonio.entregaagil.ui.adapter.viewholder

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.antonio.entregaagil.R
import com.antonio.entregaagil.constante.ASSINANTES_POR_ROTA
import com.antonio.entregaagil.modelo.Rota
import kotlinx.android.synthetic.main.item_rota.view.*

class RotasViewHolder(private val context: Context, itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun binView(rota: Rota, clickListener: ((Rota, Int) -> Unit)) {
        itemView.item_rota_descricao.text = rota.descricao
        val cadastrados = context.getString(R.string.cadastrados)
        itemView.item_rota_quantidade.text = "$cadastrados: ${rota.assinantes.size}/$ASSINANTES_POR_ROTA"
        itemView.setOnClickListener { clickListener(rota, adapterPosition) }
    }
}
