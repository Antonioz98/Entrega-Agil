package com.antonio.entregaagil.extension

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Point
import android.view.Surface
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

const val PERMISSIONS_REQUEST_LER_SMS = 1526

@SuppressLint("NewApi")
fun Activity.bloqueiaOrientacao() {
    val display = this.windowManager.defaultDisplay
    val rotation = display.rotation
    val height: Int
    val width: Int

    val size = Point()
    display.getSize(size)
    height = size.y
    width = size.x

    when (rotation) {
        Surface.ROTATION_90 -> if (width > height)
            this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        else
            this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT
        Surface.ROTATION_180 -> if (height > width)
            this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT
        else
            this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
        Surface.ROTATION_270 -> if (width > height)
            this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
        else
            this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        else -> if (height > width)
            this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        else
            this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }
}

fun Activity.desbloqueiaOrientacao() {
    this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
}

fun Activity.temPermissaoParaLerSMS(): Boolean {
    return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED
}

fun Activity.pedirPermissaoParaLerSMS() {
    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS)) {
    }
    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_SMS), PERMISSIONS_REQUEST_LER_SMS)
}