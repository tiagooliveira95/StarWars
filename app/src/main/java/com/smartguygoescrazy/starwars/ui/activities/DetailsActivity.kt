package com.smartguygoescrazy.starwars.ui.activities

import android.net.Uri
import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.smartguygoescrazy.starwars.R
import com.smartguygoescrazy.starwars.adapters.VehiclesAdapter
import com.smartguygoescrazy.starwars.models.StarWarsPeopleData
import com.smartguygoescrazy.starwars.models.StarWarsPlanet
import com.smartguygoescrazy.starwars.models.StarWarsVehicles
import com.smartguygoescrazy.starwars.sync.StarWarsApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.content_details.*
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import com.smartguygoescrazy.starwars.InternetCheck
import java.net.URLEncoder


class DetailsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_PEOPLE = "people"
    }


    private var starWarsPeople : StarWarsPeopleData? = null

    private var mAdapter = VehiclesAdapter(this)

    private var disposable: Disposable? = null

    private val starWarsApiService by lazy {
        StarWarsApiService.create()
    }

    private var mVehicleLoading: Boolean = true

    private var mPlanetLoading: Boolean = true



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        starWarsPeople = intent.getParcelableExtra(EXTRA_PEOPLE)

        details_name.text = starWarsPeople!!.name

        skinColorView.skinColor = starWarsPeople!!.skin_color.replace(",","").split("\\s".toRegex())

        gender_icon.setImageResource(getGenderResId(starWarsPeople!!.gender))

        vehicle_list.layoutManager = LinearLayoutManager(this,LinearLayout.VERTICAL, false)
        vehicle_list.adapter = mAdapter



        InternetCheck { isConnected ->
            if (isConnected) {
                beginStarWarsVehicleRequest(starWarsPeople!!.vehicles)
                beginStarWarsPlanetRequest(Uri.parse(starWarsPeople!!.homeworld).lastPathSegment!!.toInt())
            }else{
                Toast.makeText(this,"Internet not available", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.INVISIBLE
                //TODO Carregar informações da base de dados
            }
        }
    }



    private fun beginStarWarsVehicleRequest(vehiclesUrls : List<String>, i: Int = 0){
        if(vehiclesUrls.isEmpty()) {
            showEmptyListMessage()
            return
        }

        val id = Uri.parse(vehiclesUrls[i]).lastPathSegment!!.toInt()

        disposable = starWarsApiService.starWarsVehicles(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                fun(result: StarWarsVehicles) {
                    mAdapter.addItems(result)

                    if(vehiclesUrls.size > i + 1){
                        beginStarWarsVehicleRequest(vehiclesUrls, i + 1)
                    }else{
                        mVehicleLoading = true
                        setProgressBarVisibility()
                    }

                },
                { error -> Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show() }
            )
    }

    private fun showEmptyListMessage() {
        vehicle_empty_list_error.visibility = View.VISIBLE
        vehicle_list.visibility = View.INVISIBLE

    }

    private fun beginStarWarsPlanetRequest(planetId : Int){
        disposable = starWarsApiService.starWarsPlanets(planetId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                fun(result: StarWarsPlanet) {
                    planeta_natal_label.append(" ${result.name}")
                    mPlanetLoading = false
                    setProgressBarVisibility()
                },
                { error -> Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show() }
            )
    }

    @DrawableRes
    private fun getGenderResId(gender: String) : Int{
        return when(gender) {
            "male" -> R.drawable.ic_male
            "female" -> R.drawable.ic_female
            else -> R.drawable.ic_genderless
        }
    }

    private fun setProgressBarVisibility(){
        progressBar.visibility = if(mPlanetLoading && mVehicleLoading) View.VISIBLE else View.INVISIBLE
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_details,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.action_search){
            googleSearch(starWarsPeople!!.name)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun googleSearch(name: String){
        val query = URLEncoder.encode(name, "utf-8")
        val url = "http://www.google.com/search?q=$query"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)

        if(intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }else{
            Toast.makeText(this,getString(R.string.no_browser_error_message), Toast.LENGTH_SHORT).show()
        }
    }

}
