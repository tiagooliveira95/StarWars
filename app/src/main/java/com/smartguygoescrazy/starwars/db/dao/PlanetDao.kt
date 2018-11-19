package com.smartguygoescrazy.starwars.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.smartguygoescrazy.starwars.models.StarWarsPlanet

@Dao
interface PlanetDao {

    @Query("SELECT * FROM StarWarsPlanet WHERE id = :planetId")
    fun getPlanet(planetId: Int): List<StarWarsPlanet>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun  insertPeople(starWarsPlanet: StarWarsPlanet)
}