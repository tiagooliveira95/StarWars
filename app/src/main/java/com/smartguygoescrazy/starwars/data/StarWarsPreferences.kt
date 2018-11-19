package com.smartguygoescrazy.starwars.data

import android.content.Context
import android.preference.PreferenceManager

const val IS_DATA_AVAILABLE = "isDataAvailable"

class StarWarsPreferences{

    fun isOfflineDataAvailable(context: Context) : Boolean{
        return  PreferenceManager.getDefaultSharedPreferences(context).getBoolean(IS_DATA_AVAILABLE, false)
    }

    fun setIsOfflineDataAvailable(context: Context, boolean: Boolean){
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(IS_DATA_AVAILABLE,boolean).apply()
    }
}