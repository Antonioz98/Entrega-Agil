package com.antonio.entregaagil.extension

import android.graphics.Paint
import android.widget.TextView

fun TextView.colocaUnderline() {
    this.paintFlags =
        this.paintFlags or Paint.UNDERLINE_TEXT_FLAG
}