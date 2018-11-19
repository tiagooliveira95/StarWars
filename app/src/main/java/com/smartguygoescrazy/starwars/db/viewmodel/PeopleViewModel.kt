package com.smartguygoescrazy.starwars.db.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.smartguygoescrazy.starwars.db.AppDatabase
import com.smartguygoescrazy.starwars.db.DataRepository
import com.smartguygoescrazy.starwars.models.StarWarsPeopleData


class PeopleViewModel(application: Application) : AndroidViewModel(application) {

    private val mRepository = DataRepository(application)
    private val peopleDao = AppDatabase.getInstance(application)!!.peopleDao()
    private val mAllPeople : LiveData<List<StarWarsPeopleData>> = peopleDao.getAllPeople()


    fun getAllPeople() : LiveData<List<StarWarsPeopleData>>{
        return mAllPeople
    }

    fun insert(starWarsPeople: List<StarWarsPeopleData>){
        mRepository.insertPeople(starWarsPeople)
    }


}