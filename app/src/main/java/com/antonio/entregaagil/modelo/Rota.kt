package com.antonio.entregaagil.modelo

import android.os.Parcel
import android.os.Parcelable

class Rota() : Parcelable {

    var id: String = ""
    var descricao: String = "Rota padrao"
    var assinantes: List<Assinante> = listOf()

    constructor(parcel: Parcel) : this() {
        id = parcel.readString().toString()
        descricao = parcel.readString().toString()
        assinantes = (parcel.createTypedArrayList(Assinante) as List<Assinante>?)!!
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(descricao)
        parcel.writeTypedList(assinantes)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Rota> {
        override fun createFromParcel(parcel: Parcel): Rota {
            return Rota(parcel)
        }

        override fun newArray(size: Int): Array<Rota?> {
            return arrayOfNulls(size)
        }
    }
}