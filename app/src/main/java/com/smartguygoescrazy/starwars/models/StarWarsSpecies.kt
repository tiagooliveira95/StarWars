package com.smartguygoescrazy.starwars.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.net.Uri

data class StarWarsSpecies(
    val count: Int,
    val next: String?,
    val previous: Any,
    val results: List<StarWarsSpeciesData>
)

@Entity
data class StarWarsSpeciesData(
    @ColumnInfo val name: String,
    val url: String,
    @PrimaryKey(autoGenerate = false) val id: Int = Uri.parse(url).lastPathSegment!!.toInt()

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
)