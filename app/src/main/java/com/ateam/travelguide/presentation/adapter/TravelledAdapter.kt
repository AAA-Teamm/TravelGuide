package com.ateam.travelguide.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ateam.travelguide.databinding.LocationTravelledBinding
import com.ateam.travelguide.model.Image
import com.ateam.travelguide.model.Location
import com.ateam.travelguide.util.TravelledViewHolder

class TravelledAdapter(val context : Context, val pairList : ArrayList<Pair<Location, Image?>>,val itemClick : ((position : Int)->Unit)) : RecyclerView.Adapter<TravelledViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravelledViewHolder {

        val binding = LocationTravelledBinding.inflate(LayoutInflater.from(context), parent, false)

        return TravelledViewHolder(binding.root, itemClick)
    }

    override fun onBindViewHolder(holder: TravelledViewHolder, position: Int) {

       holder.bindData(context, pairList[position].first, pairList[position].second)
    }

    override fun getItemCount(): Int {

        return pairList.size
    }
}