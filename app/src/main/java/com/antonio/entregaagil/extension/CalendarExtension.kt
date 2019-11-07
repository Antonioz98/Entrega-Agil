package com.antonio.entregaagil.extension

import java.util.*

fun Calendar.inicioDoDia(): Calendar {
    configuraTempo(this, 0, 0, 0)
    return this
}

fun Calendar.finalDoDia(): Calendar {
    configuraTempo(this, 23, 59, 59)
    return this
}

private fun configuraTempo(calendario: Calendar, horas: Int, minutos: Int, segundos: Int) {
    calendario.set(Calendar.HOUR_OF_DAY, horas)
    calendario.set(Calendar.MINUTE, minutos)
    calendario.set(Calendar.SECOND, segundos)
}