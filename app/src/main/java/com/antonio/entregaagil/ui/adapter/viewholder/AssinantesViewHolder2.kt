package com.antonio.entregaagil.ui.adapter.viewholder

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.antonio.entregaagil.R
import com.antonio.entregaagil.modelo.Assinante
import kotlinx.android.synthetic.main.item_assinante2.view.*

class AssinantesViewHolder2(private val context: Context, itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    fun binView(assinante: Assinante, clickListener: ((Assinante, Int) -> Unit)) {
        itemView.assinantes_nome2.text = assinante.nome
        val endereco = context.getString(R.string.endereco)
        val numero = context.getString(R.string.numero)
        val baiiro = context.getString(R.string.bairro)
        itemView.assinante_endereco2.text = "$endereco: ${assinante.endereco}"
        itemView.assinante_numero2.text = "$numero: ${assinante.numero}"
        itemView.assinante_bairro2.text = "$baiiro: ${assinante.bairro}"
        itemView.setOnClickListener { clickListener(assinante, adapterPosition) }
    }
}
