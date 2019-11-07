package com.antonio.entregaagil.ui.adapter.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.antonio.entregaagil.R
import com.antonio.entregaagil.ui.adapter.viewholder.AssinantesViewHolder

class AssinantesAdapter(private val context: Context) : RecyclerView.Adapter<AssinantesViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssinantesViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_assinante, null, false)

        return AssinantesViewHolder(context, view)
    }

    override fun getItemCount(): Int {
        return 0
    }

    override fun onBindViewHolder(holder: AssinantesViewHolder, position: Int) {
        holder.binView()
    }

}
