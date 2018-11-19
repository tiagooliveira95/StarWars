package com.smartguygoescrazy.starwars.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.smartguygoescrazy.starwars.R
import com.smartguygoescrazy.starwars.models.StarWarsVehicles
import kotlinx.android.synthetic.main.content_vehicles_item.view.*


class VehiclesAdapter(private val context : Context?) :
    RecyclerView.Adapter<VehiclesAdapter.ViewHolder>() {

    var items : ArrayList<StarWarsVehicles> = arrayListOf()


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.vehicles_item,parent, false))
    }

    override fun getItemCount(): Int {
        return  items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        val data = items[pos]

        holder.vehicleName.text = data.name
        holder.vehicleClass.text = data.vehicle_class.capitalize()
    }


    fun addItems (newItems : List<StarWarsVehicles>) {
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun addItems (newItem : StarWarsVehicles) {
        items.add(newItem)
        notifyDataSetChanged()
    }

    inner class ViewHolder (v: View) : RecyclerView.ViewHolder(v) {
        val vehicleName = v.name!!
        val vehicleClass = v.vehicle_class!!
    }
}