package com.smartguygoescrazy.starwars.sync

import com.smartguygoescrazy.starwars.models.StarWarsPeople
import com.smartguygoescrazy.starwars.models.StarWarsPlanet
import com.smartguygoescrazy.starwars.models.StarWarsSpecies
import com.smartguygoescrazy.starwars.models.StarWarsVehicles
import com.smartguygoescrazy.starwars.sync.StarWarsApiService.Companion.SPECIES_PATH
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface StarWarsApiService {


    @GET(PEOPLE_PATH)
    fun starWarsPeople(
        @Query("page") page: Int?
    ): Observable<StarWarsPeople>

    @GET("$VEHICLES_PATH/{id}")
    fun starWarsVehicles(@Path("id") vehicleId: Int
    ): Observable<StarWarsVehicles>

    @GET("$PLANET_PATH/{id}")
    fun starWarsPlanets(@Path("id") planetId: Int)
            : Observable<StarWarsPlanet>

    @GET(SPECIES_PATH)
    fun starWarsSpecies(@Query("page") page: Int)
            : Observable<StarWarsSpecies>

    companion object {
        private const val STAR_WARS_BASE_URL : String = "https://swapi.co/api/"
        const val PEOPLE_PATH = "people"
        const val PLANET_PATH = "planets"
        const val VEHICLES_PATH = "vehicles"
        const val SPECIES_PATH = "vehicles"

        fun create(): StarWarsApiService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create()
                )
                .addConverterFactory(
                    GsonConverterFactory.create()
                )
                .baseUrl(STAR_WARS_BASE_URL)
                .build()

            return retrofit.create(StarWarsApiService::class.java)
        }
    }

}


