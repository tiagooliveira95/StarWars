package com.smartguygoescrazy.starwars.db.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.smartguygoescrazy.starwars.db.AppDatabase
import com.smartguygoescrazy.starwars.db.DataRepository
import com.smartguygoescrazy.starwars.models.StarWarsSpeciesData

class SpeciesViewModel(application: Application) : AndroidViewModel(application) {

    private val mRepository = DataRepository(application)
    private val speciesDao = AppDatabase.getInstance(application)!!.speciesDao()

    private val mAllSpecies : LiveData<List<StarWarsSpeciesData>> = speciesDao.getAllSpecies()


    fun getAllSpecies() : LiveData<List<StarWarsSpeciesData>> {
        return mAllSpecies
    }

    fun insert(tarWarsSpeciesData: List<StarWarsSpeciesData>){
        mRepository.insertSpecie(tarWarsSpeciesData)
    }




}