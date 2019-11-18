package com.antonio.entregaagil.modelo

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable
import java.util.*

class Assinante() : Parcelable {
    var id: String = UUID.randomUUID().toString()
    var nome: String = ""
    var endereco: String = ""
    var bairro: String = ""
    var complemento: String = ""
    var numero: Int = 0
    var rota: String? = null
    var inicioAssinatura: Date? = null
    var fimAssinatura: Date? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readString().toString()
        nome = parcel.readString().toString()
        endereco = parcel.readString().toString()
        bairro = parcel.readString().toString()
        complemento = parcel.readString().toString()
        numero = parcel.readInt()
        rota = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(nome)
        parcel.writeString(endereco)
        parcel.writeString(bairro)
        parcel.writeString(complemento)
        parcel.writeInt(numero)
        parcel.writeString(rota)
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