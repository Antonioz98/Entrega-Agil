package com.antonio.entregaagil.extension

import android.content.Context
import android.net.ConnectivityManager

fun Context.temConexaoComInternet(): Boolean {
    val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val ni = cm.activeNetworkInfo
    return ni != null && ni.isConnected
}