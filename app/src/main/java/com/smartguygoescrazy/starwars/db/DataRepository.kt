package com.smartguygoescrazy.starwars.db

import android.app.Application
import com.smartguygoescrazy.starwars.AppExecutors
import com.smartguygoescrazy.starwars.db.dao.PeopleDao
import com.smartguygoescrazy.starwars.db.dao.PlanetDao
import com.smartguygoescrazy.starwars.db.dao.VehicleDao
import com.smartguygoescrazy.starwars.models.StarWarsPeopleData
import com.smartguygoescrazy.starwars.models.StarWarsVehicles

class DataRepository(application: Application){

    private val appDatabase : AppDatabase = AppDatabase.getInstance(application)!!

    private var peopleDao : PeopleDao = appDatabase.peopleDao()
    private var planetDao : PlanetDao = appDatabase.planetDao()
    private var vehicleDao: VehicleDao = appDatabase.vehicleDao()


    fun insertVehicle(starWarsVehicles: StarWarsVehicles){
        AppExecutors.instance!!.diskIO().execute {
            vehicleDao.insertVehicle(starWarsVehicles)
        }
    }

    fun insertPeople(starWarsPeopleData: List<StarWarsPeopleData>){
        AppExecutors.instance!!.diskIO().execute {
            peopleDao.insertPeople(starWarsPeopleData)
        }
    }



}