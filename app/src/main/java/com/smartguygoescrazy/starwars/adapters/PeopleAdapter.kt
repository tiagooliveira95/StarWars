package com.smartguygoescrazy.starwars.adapters

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.smartguygoescrazy.starwars.R
import com.smartguygoescrazy.starwars.models.StarWarsPeopleData
import com.smartguygoescrazy.starwars.models.StarWarsSpecies
import com.smartguygoescrazy.starwars.models.StarWarsSpeciesData
import kotlinx.android.synthetic.main.content_people_item.view.*

class PeopleAdapter(private val context : Context?, val listener: (StarWarsPeopleData) -> Unit) :
    RecyclerView.Adapter<PeopleAdapter.ViewHolder>() {

    var items : ArrayList<StarWarsPeopleData> = arrayListOf()
    var species : ArrayList<StarWarsSpeciesData?> = arrayListOf()

    var isDataOffline = false


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.people_item,parent, false))
    }

    override fun getItemCount(): Int {
        return  items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        val data = items[pos]

        holder.characterName.text = data.name

        var speciesText = ""


        for(i in data.species.indices){
            var specieId = Uri.parse(data.species[i]).lastPathSegment?.toInt()

            if(isDataOffline) specieId = specieId?.minus(1)


            if(specieId != null && species.size > specieId && species[specieId] != null){

                speciesText += if(i == 0 )
                    species[specieId]?.name
                else
                    ", ${species[specieId]?.name}"

                holder.characterRace.text = speciesText
            }
        }

        val vehiclesText = "Veículos: ${data.vehicles.size}"
        holder.characterVehicleCount.text = vehiclesText
    }


    fun addItems (newItems : List<StarWarsPeopleData>) {
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun addSpecies(newSpecies: StarWarsSpecies){
        if(species.isEmpty()){
            species.addAll(arrayOfNulls<StarWarsSpeciesData>(newSpecies.count + 1))
        }

        for(specie in newSpecies.results){
            val id = Uri.parse(specie.url).lastPathSegment?.toInt()
            species[id!!] = specie
        }
        notifyDataSetChanged()
    }

    fun addSpecies(newSpiceis: List<StarWarsSpeciesData>) {
        species.addAll(newSpiceis)
    }



    inner class ViewHolder (v: View) : RecyclerView.ViewHolder(v) {
        val characterName = v.name!!
        val characterRace = v.race!!
        val characterVehicleCount = v.vehicleCount!!

      init {
          v.setOnClickListener { listener(items[adapterPosition]) }
      }

    }
}



