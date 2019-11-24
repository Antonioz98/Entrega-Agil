package com.antonio.entregaagil.ui.adapter.viewholder

import android.content.Context
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.antonio.entregaagil.R
import com.antonio.entregaagil.extension.formataDataResumida
import com.antonio.entregaagil.modelo.Assinante
import kotlinx.android.synthetic.main.item_assinante.view.*
import kotlin.random.Random

class AssinantesViewHolder(private val context: Context, itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    fun binView(assinante: Assinante, clickListener: ((Assinante, Int) -> Unit)) {
        itemView.assinantes_nome.text = assinante.nome
        itemView.assinante_endereco.text = "${assinante.endereco}, ${assinante.numero}"
        itemView.assinante_bairro.text = "${context.getString(R.string.bairro)}: ${assinante.bairro}"
        bindData(assinante)
        itemView.setOnClickListener { clickListener(assinante, adapterPosition) }

        if (assinante.rota == null) {
            itemView.assinante_sem_rota.visibility = VISIBLE
        } else {
            itemView.assinante_sem_rota.visibility = GONE
        }


        val random = Random(3)
        random.nextInt()
        val drawable =
            TextDrawable.builder()
                .buildRound(assinante.nome[0].toString(), ContextCompat.getColor(context, R.color.colorAccent))
        itemView.assinante_inicial.setImageDrawable(drawable)
    }

    private fun bindData(assinante: Assinante) {
        val de = assinante.inicioAssinatura?.formataDataResumida()
        val ate = assinante.fimAssinatura?.formataDataResumida()

        val ateString = context.getString(R.string.ate)
        itemView.assinante_perido_assinatura.text = "${de ?: ""} $ateString ${ate ?: ""}"
        if (itemView.assinante_perido_assinatura.text.length <= 5) {
            itemView.assinante_perido_assinatura.visibility = GONE
        } else {
            itemView.assinante_perido_assinatura.visibility = VISIBLE
        }
    }
}
