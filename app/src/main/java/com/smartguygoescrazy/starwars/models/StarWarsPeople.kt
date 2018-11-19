package com.smartguygoescrazy.starwars.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

data class StarWarsPeople(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<StarWarsPeopleData>
)

@Entity
data class StarWarsPeopleData(
    // Define se o nome da coluna, assim caso haja alteraçãos ao API  podemos atualizar o nome da
    // da variavel sem ter que afectar a tabela
    @PrimaryKey(autoGenerate = true) var id : Int,
    @ColumnInfo(name = "homeworld") var homeworld: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "skinColor") var skin_color: String,
    @ColumnInfo(name = "species") var species: List<String>,
    @ColumnInfo(name = "vehicles") var vehicles: List<String>,
    @ColumnInfo(name = "gender") var gender: String
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.createStringArrayList(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(homeworld)
        parcel.writeString(name)
        parcel.writeString(skin_color)
        parcel.writeStringList(species)
        parcel.writeStringList(vehicles)
        parcel.writeString(gender)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StarWarsPeopleData> {
        override fun createFromParcel(parcel: Parcel): StarWarsPeopleData {
            return StarWarsPeopleData(parcel)
        }

        override fun newArray(size: Int): Array<StarWarsPeopleData?> {
            return arrayOfNulls(size)
        }
    }
}