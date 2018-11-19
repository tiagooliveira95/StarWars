package com.smartguygoescrazy.starwars.ui.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.smartguygoescrazy.starwars.InternetCheck
import com.smartguygoescrazy.starwars.R
import com.smartguygoescrazy.starwars.StarWarsRecyclerOnScrollListener
import com.smartguygoescrazy.starwars.adapters.PeopleAdapter
import com.smartguygoescrazy.starwars.db.viewmodel.PeopleViewModel
import com.smartguygoescrazy.starwars.db.viewmodel.SpeciesViewModel
import com.smartguygoescrazy.starwars.models.StarWarsPeople
import com.smartguygoescrazy.starwars.models.StarWarsPeopleData
import com.smartguygoescrazy.starwars.models.StarWarsSpecies
import com.smartguygoescrazy.starwars.models.StarWarsSpeciesData
import com.smartguygoescrazy.starwars.sync.StarWarsApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

private const val EXTRA_PEOPLE_LIST = "peopleList"
private const val EXTRA_PEOPLE_SPECIES = "peopleSpecies"

class MainActivity : AppCompatActivity() {


    private var mPage = 1



    private var mAdapter: PeopleAdapter = PeopleAdapter(this) { starWarsPeopleData ->
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(DetailsActivity.EXTRA_PEOPLE, starWarsPeopleData)
        startActivity(intent)
    }

    private var disposable: Disposable? = null

    private val starWarsApiService by lazy {
        StarWarsApiService.create()
    }

    private var mPeopleViewModel: PeopleViewModel? = null

    private var mSpeciesViewModel: SpeciesViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mPeopleViewModel = ViewModelProviders.of(this).get(PeopleViewModel::class.java)
        mSpeciesViewModel = ViewModelProviders.of(this).get(SpeciesViewModel::class.java)

        peopleRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        peopleRecyclerView.adapter = mAdapter





        //Verifica se o bundle é nulo ou se a lista de items esta vazia
        if (savedInstanceState == null || mAdapter.items.isEmpty()) {

            InternetCheck { isConnected ->
                if (isConnected) {
                    peopleRecyclerView.addOnScrollListener(starWarsOnScrollListener) // Listener para auto preencher a lista

                    beginStarWarsPeoplePageRequest(1)

                    // Obtem se todas as especies logo ao inicio, desta forma apenas temos que fazer 4 requests ao servidor
                    // evitando assim fazer um request por cada personagem
                    beginStarWarsSpeciesPageRequest(1)
                }else{
                    mAdapter.isDataOffline = true
                    queryDatabase()
                }
            }
        } else if (savedInstanceState.containsKey(EXTRA_PEOPLE_LIST)) {
            @Suppress("UNCHECKED_CAST")
            mAdapter.items =
                    savedInstanceState.getSerializable(EXTRA_PEOPLE_LIST) as ArrayList<StarWarsPeopleData> // Obtem a lista de itens
            @Suppress("UNCHECKED_CAST")
            mAdapter.addSpecies(
                    savedInstanceState.getSerializable(EXTRA_PEOPLE_SPECIES) as ArrayList<StarWarsSpeciesData>) // Obtem a lista de especies
            mAdapter.notifyDataSetChanged() // Notifica o adapter que houve uma alteração
        }
    }


    private fun queryDatabase() {
        mPeopleViewModel!!.getAllPeople().observe(this, Observer {
            if (it != null) {
                mAdapter.addItems(it)
                main_progressBar.visibility = View.INVISIBLE
            }
        })

        mSpeciesViewModel!!.getAllSpecies().observe(this, Observer {
            if (it != null) {
                mAdapter.addSpecies(it)
                mAdapter.notifyDataSetChanged()
            }
        })


    }

    /**
     *  Guarda a lista de personagens no bundle antes de destruir a atividade
     */
    override fun onRestoreInstanceState(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        savedInstanceState!!.putSerializable(EXTRA_PEOPLE_LIST, mAdapter.items)
        savedInstanceState.putSerializable(EXTRA_PEOPLE_SPECIES, mAdapter.species)
        super.onRestoreInstanceState(savedInstanceState, persistentState)
    }

    /**
     * @param page passar null para obter a pagina 0
     */
    private fun beginStarWarsPeoplePageRequest(page: Int) {
        disposable = starWarsApiService.starWarsPeople(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                fun(result: StarWarsPeople) {
                    mAdapter.addItems(result.results)

                    val hasNext = result.next == null

                    starWarsOnScrollListener.endReached = hasNext

                    main_progressBar.visibility = View.INVISIBLE
                    mPeopleViewModel!!.insert(result.results)
                },
                { error ->
                    Toast.makeText(this, "beginStarWarsPeoplePageRequest" + error.message, Toast.LENGTH_LONG).show()
                }
            )
    }

    /**
     * Obtem todas as especies do API
     */
    private fun beginStarWarsSpeciesPageRequest(page: Int) {
        disposable = starWarsApiService.starWarsSpecies(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                fun(result: StarWarsSpecies) {
                    mAdapter.addSpecies(result)

                    val swp = result.results
                    for(i in swp.indices){
                        swp[i].id = Uri.parse(swp[i].url).lastPathSegment!!.toInt()
                    }

                    mSpeciesViewModel?.insert(swp)

                    if (result.next != null) {
                        beginStarWarsSpeciesPageRequest(page + 1)
                    }
                },
                { error ->
                    Toast.makeText(this, "beginStarWarsSpeciesPageRequest: $error.message", Toast.LENGTH_LONG).show()
                }
            )
    }


    /**
     * OnScrollListener utilizado para detectar quando a lista se aproxima do fim
     * com o intuito de carregar mais elementos, evitando assim carregar todos os
     * items sem necessidade
     */
    private val starWarsOnScrollListener = object : StarWarsRecyclerOnScrollListener() {
        override fun onLoadRequest() {
            beginStarWarsPeoplePageRequest(++mPage)
            main_progressBar.visibility = View.VISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (item!!.itemId == R.id.action_sobre) {
            startActivity(Intent(this, Sobre::class.java))
            true
        } else super.onOptionsItemSelected(item)
    }
}
