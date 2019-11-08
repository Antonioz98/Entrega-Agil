package com.antonio.entregaagil.modelo

import android.os.Parcel
import android.os.Parcelable
import java.util.*

class Assinante() : Parcelable {
    var id: String = ""
    var nome: String = "Antonio Testador"
    var endereco: String = "Rua das olivas"
    var bairro: String = "Winterfell do sul"
    var complemento: String = "Winterfell do sul"
    var numero: Int = 83
    var inicioAssinatura: Date? = null
    var fimAssinatura: Date? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readString().toString()
        nome = parcel.readString().toString()
        endereco = parcel.readString().toString()
        complemento = parcel.readString().toString()
        bairro = parcel.readString().toString()
        numero = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(nome)
        parcel.writeString(endereco)
        parcel.writeString(complemento)
        parcel.writeString(bairro)
        parcel.writeInt(numero)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Assinante> {
        override fun createFromParcel(parcel: Parcel): Assinante {
            return Assinante(parcel)
        }

        override fun newArray(size: Int): Array<Assinante?> {
            return arrayOfNulls(size)
        }
    }
}