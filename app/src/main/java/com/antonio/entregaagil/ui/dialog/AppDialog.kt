package com.antonio.entregaagil.ui.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.antonio.entregaagil.R
import kotlinx.android.synthetic.main.dialog_fragment_custom.view.*

class AppDialog(private val context: Context, viewGroup: ViewGroup) {

    private val viewCriada = LayoutInflater.from(context).inflate(R.layout.dialog_fragment_custom, viewGroup, false)

    fun chama(mensagem: String) {
        constroiDialog(mensagem)
    }

    fun chama(mensagem: String, botaoExtra: ((delegate: Void?) -> Unit), tiuloBotaoExtra: String) {
        val dialog = constroiDialog(mensagem)

        with(viewCriada.dialog_custom_botao_adicional) {
            visibility = View.VISIBLE
            text = tiuloBotaoExtra
            setOnClickListener {
                fecha(dialog)
                botaoExtra(null)
            }
        }
    }

    private fun constroiDialog(mensagem: String): AlertDialog {
        viewCriada.dialog_custom_mensagem_textView.text = mensagem

        val dialog = AlertDialog.Builder(context)
            .setView(viewCriada)
            .show().apply {
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }

        defineBotaOk(dialog)

        return dialog
    }

    private fun defineBotaOk(dialog: AlertDialog) {
        viewCriada.dialog_custom_ok_button.setOnClickListener {
            fecha(dialog)
        }
    }

    private fun fecha(dialog: AlertDialog) {
        dialog.dismiss()
    }
}