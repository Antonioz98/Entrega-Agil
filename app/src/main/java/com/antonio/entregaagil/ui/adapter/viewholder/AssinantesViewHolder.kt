package com.antonio.entregaagil.ui.adapter.viewholder

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.antonio.entregaagil.R
import com.antonio.entregaagil.modelo.Assinante
import kotlinx.android.synthetic.main.item_assinante.view.*

class AssinantesViewHolder(private val context: Context, itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    fun binView(assinante: Assinante, clickListener: ((Assinante, Int) -> Unit)) {
        itemView.assinantes_nome.text = assinante.nome
        itemView.assinante_endereco.text = "${assinante.endereco} N ${assinante.numero}"
        itemView.assinante_bairro.text = assinante.bairro
        itemView.assinante_perido_assinatura.text =
            "${assinante.inicioAssinatura ?: "----"} até ${assinante.fimAssinatura ?: "----"}"
        itemView.setOnClickListener { clickListener(assinante, adapterPosition) }

        val drawable =
            TextDrawable.builder()
                .buildRound(assinante.nome[0].toString(), ContextCompat.getColor(context, R.color.amareloClaro))
        itemView.assinante_inicial.setImageDrawable(drawable)
    }
}
