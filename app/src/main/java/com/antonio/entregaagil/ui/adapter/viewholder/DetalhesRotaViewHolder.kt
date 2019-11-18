package com.antonio.entregaagil.ui.adapter.viewholder

import android.content.Context
import android.view.View
import android.view.View.GONE
import androidx.recyclerview.widget.RecyclerView
import com.antonio.entregaagil.modelo.Assinante
import kotlinx.android.synthetic.main.item_detalhe_rota.view.*

const val NORMAL = 0
const val DESVINCULAR = 1
class DetalhesRotaViewHolder(private val context: Context, itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun binView(assinante: Assinante, clickListener: ((Assinante, Int) -> Unit)) {
        itemView.detalhe_rota_nome.text = assinante.nome
        itemView.detalhe_rota_endereco.text = assinante.endereco
        itemView.detalhe_rota_numero.text = "${assinante.numero}"
        itemView.detalhe_rota_bairro.text = assinante.bairro

        if (assinante.complemento.isEmpty()) {
            itemView.detalhe_rota_complemento_ref.visibility = GONE
            itemView.detalhe_rota_complemento.visibility = GONE
        } else {
            itemView.detalhe_rota_complemento_ref.visibility = GONE
            itemView.detalhe_rota_complemento.visibility = GONE
            itemView.detalhe_rota_complemento.text = assinante.complemento
        }

        itemView.setOnClickListener { clickListener(assinante,NORMAL ) }
        itemView.detalhe_rota_desvincular.setOnClickListener { clickListener(assinante,DESVINCULAR )}
    }
}
