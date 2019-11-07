package com.antonio.entregaagil.extension

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

private const val UTC = "UTC"
@SuppressLint("SimpleDateFormat")
private val utcFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")

fun String.converteParaDate(): Date? {

    utcFormat.timeZone = TimeZone.getTimeZone(UTC)
    return try {
        utcFormat.parse(this)
    } catch (e: ParseException) {
        null
    }
}