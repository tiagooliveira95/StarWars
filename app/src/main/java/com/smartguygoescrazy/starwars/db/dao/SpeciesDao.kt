package com.smartguygoescrazy.starwars.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.smartguygoescrazy.starwars.models.StarWarsSpeciesData

@Dao
interface SpeciesDao {

    @Query("SELECT * FROM StarWarsSpeciesData ORDER BY id ASC")
    fun getAllSpecies() : LiveData<List<StarWarsSpeciesData>>

    @Insert
    fun insertSpecies(starWarsSpeciesData: List<StarWarsSpeciesData>)
}