package com.smartguygoescrazy.starwars.db

import android.arch.persistence.room.*
import android.content.Context
import com.smartguygoescrazy.starwars.db.dao.PeopleDao
import com.smartguygoescrazy.starwars.db.dao.PlanetDao
import com.smartguygoescrazy.starwars.db.dao.VehicleDao
import com.smartguygoescrazy.starwars.models.*

@Database( entities = [
    StarWarsPeopleData::class,
    StarWarsPlanet::class,
    StarWarsVehicles::class,
    StarWarsSpeciesData::class],
    version = 1,
    exportSchema = false )
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun peopleDao() : PeopleDao
    abstract fun planetDao() : PlanetDao
    abstract fun vehicleDao(): VehicleDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "starwars")
                        .build()
                }
            }
            return INSTANCE
        }
    }


}