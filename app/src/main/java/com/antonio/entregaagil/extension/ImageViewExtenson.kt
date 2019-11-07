package com.antonio.entregaagil.extension

import android.content.Context
import android.widget.ImageView
import androidx.core.content.ContextCompat

fun ImageView.setDrawable(context: Context, idDrawable: Int) {
    setImageDrawable(
        ContextCompat.getDrawable(
            context,
            idDrawable
        )
    )
}