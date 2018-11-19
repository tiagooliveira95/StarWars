package com.smartguygoescrazy.starwars.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

data class StarWarsSpecies(
    val count: Int,
    val next: String?,
    val previous: Any,
    val results: List<StarWarsSpeciesData>
)

@Entity()
data class StarWarsSpeciesData(
    @ColumnInfo val name: String,
    val url: String,
    @PrimaryKey(autoGenerate = false) var id : Int



/*
    val average_height: String,
    val average_lifespan: String,
    val classification: String,
    val created: String,
    val designation: String,
    val edited: String,
    val eye_colors: String,
    val films: List<String>,
    val hair_colors: String,
    val homeworld: String,
    val language: String,
    val people: List<String>,
    val skin_colors: String,*/
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(url)
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StarWarsSpeciesData> {
        override fun createFromParcel(parcel: Parcel): StarWarsSpeciesData {
            return StarWarsSpeciesData(parcel)
        }

        override fun newArray(size: Int): Array<StarWarsSpeciesData?> {
            return arrayOfNulls(size)
        }
    }
}