package com.smartguygoescrazy.starwars.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.smartguygoescrazy.starwars.models.StarWarsVehicles

@Dao
interface VehicleDao{

    @Query("SELECT * FROM StarWarsVehicles WHERE id = :vehicleId")
    fun getVehicle(vehicleId: Int): List<StarWarsVehicles>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun  insertVehicle(starWarsVehicles: StarWarsVehicles)
}