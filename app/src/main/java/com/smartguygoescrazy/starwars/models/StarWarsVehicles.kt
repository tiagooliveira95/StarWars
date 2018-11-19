package com.smartguygoescrazy.starwars.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.net.Uri


@Entity
data class StarWarsVehicles(
    val url: String,
    @ColumnInfo val vehicle_class: String,
    @ColumnInfo val name: String,
    @PrimaryKey(autoGenerate = false) val id: Int = Uri.parse(url).lastPathSegment!!.toInt()

  /*  val cargo_capacity: String,
    val consumables: String,
    val cost_in_credits: String,
    val created: String,
    val crew: String,
    val edited: String,
    val films: List<String>,
    val length: String,
    val manufacturer: String,
    val max_atmosphering_speed: String,
    val model: String,
    val passengers: String,
    val pilots: List<String>*/

)
