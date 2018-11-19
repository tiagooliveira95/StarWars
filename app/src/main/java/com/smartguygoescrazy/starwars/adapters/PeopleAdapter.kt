package com.smartguygoescrazy.starwars.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.smartguygoescrazy.starwars.R
import com.smartguygoescrazy.starwars.models.StarWarsPeopleData
import kotlinx.android.synthetic.main.content_people_item.view.*

class PeopleAdapter(private val context : Context?, val listener: (StarWarsPeopleData) -> Unit) :
    RecyclerView.Adapter<PeopleAdapter.ViewHolder>() {

    var items : ArrayList<StarWarsPeopleData> = arrayListOf()


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.people_item,parent, false))
    }

    override fun getItemCount(): Int {
        return  items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        val data = items[pos]

        holder.characterName.text = data.name
        holder.characterRace.text = data.species[0]

        holder.characterVehicleCount.text = data.vehicles.size.toString()
    }


    fun addItems (newItems : List<StarWarsPeopleData>) {
        items.addAll(newItems)
        notifyDataSetChanged()
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



