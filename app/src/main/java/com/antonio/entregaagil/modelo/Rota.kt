package com.antonio.entregaagil.modelo

import android.os.Parcel
import android.os.Parcelable
import java.util.*

class Rota() : Parcelable {

    var id: String = UUID.randomUUID().toString()
    var descricao: String = "Rota padrao"
    var assinantes: MutableList<Assinante> = mutableListOf()

    constructor(parcel: Parcel) : this() {
        id = parcel.readString().toString()
        descricao = parcel.readString().toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(descricao)
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