package com.smartguygoescrazy.starwars.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.smartguygoescrazy.starwars.models.StarWarsPeopleData

@Dao
interface PeopleDao {

    @Query("SELECT * FROM StarWarsPeopleData")
    fun getAllPeople() : LiveData<List<StarWarsPeopleData>>

    @Insert
    fun insertPeople(starWarsPeopleData: List<StarWarsPeopleData>)
}