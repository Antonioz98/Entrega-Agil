package com.antonio.entregaagil.extension

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.antonio.entregaagil.ui.dialog.AppDialog


fun Fragment.mostraDialog(mensagem: String) {
    val viewGroup = activity?.window?.decorView as ViewGroup
    context?.let { context ->
        AppDialog(context, viewGroup).chama(mensagem = mensagem)
    }
}

fun Fragment.mostraDialog(mensagem: String, tiuloBotaoExtra: String, botaoExtra: ((delegate: Void?) -> Unit)) {
    val viewGroup = activity?.window?.decorView as ViewGroup
    context?.let { context ->
        AppDialog(context, viewGroup).chama(mensagem, botaoExtra, tiuloBotaoExtra)
    }
}

fun Fragment.vibrarCelular() {
    val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    if (Build.VERSION.SDK_INT >= 26) {
        vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        vibrator.vibrate(200)
    }
}