package com.antonio.entregaagil.extension

import android.annotation.SuppressLint
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

private const val UTC = "UTC"
@SuppressLint("SimpleDateFormat")
private val utcFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
private val resumidoFormat = SimpleDateFormat("dd/MM/yyyy")

fun Date.converteParaString(): String {
    return utcFormat.format(this)
}

fun Date.ehPosDataAtual(): Boolean {
    return this.after(Date())
}

fun Date.formataDataComDiaDaSemana(): String {
    return DateFormat.getDateInstance(DateFormat.FULL).format(this)
}

fun Date.formataDataResumida(): String {
    return resumidoFormat.format(this)
}

@SuppressLint("SimpleDateFormat")
fun Date.formataParaHora(): String {
    return SimpleDateFormat("HH:mm:ss").format(this)
}

@SuppressLint("SimpleDateFormat")
fun Date.getDiferencaEntreDataAtual(): String {

    val stringDataAtual = Date().converteParaString()
    val dataAtual = stringDataAtual.converteParaDate()

    val minSeg = SimpleDateFormat("mm:ss")

    if (dataAtual != null) {
        return minSeg.format(this.time - dataAtual.time)
    } else {
        return "00:00"
    }
}