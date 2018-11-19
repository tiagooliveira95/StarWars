package com.smartguygoescrazy.starwars.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class StarWarsPlanet(
    @ColumnInfo val name: String,
    val url: String,
    @PrimaryKey(autoGenerate = false) val id : Int



/*
    val climate: String,
    val created: String,
    val diameter: String,
    val edited: String,
    val films: List<String>,
    val gravity: String,
    val orbital_period: String,
    val population: String,
    val residents: List<String>,
    val rotation_period: String,
    val surface_water: String,
    val terrain: String*/
)
