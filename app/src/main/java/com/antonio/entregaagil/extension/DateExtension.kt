package com.antonio.entregaagil.extension

import android.annotation.SuppressLint
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
private val resumidoFormat = SimpleDateFormat("dd/MM/yyyy")


fun Date.estaExpirado(): Boolean {
    return this.after(Date())
}

fun Date.formataDataResumida(): String {
    return resumidoFormat.format(this)
}
