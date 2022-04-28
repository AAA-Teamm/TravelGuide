package com.ateam.travelguide.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ateam.travelguide.R
import com.ateam.travelguide.databinding.LocationToBeTravelledBinding
import com.ateam.travelguide.model.Image
import com.ateam.travelguide.model.Location
import com.ateam.travelguide.util.ToBeTravelledViewHolder

class ToBeTravelledAdapter(val context : Context, val pairList : ArrayList<Pair<Location, Image?>>,val itemClick : ((position : Int)->Unit)) : RecyclerView.Adapter<ToBeTravelledViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToBeTravelledViewHolder {

        val binding = LocationToBeTravelledBinding.inflate(LayoutInflater.from(context), parent, false)

        return ToBeTravelledViewHolder(binding.root, itemClick)
    }

    override fun onBindViewHolder(holder: ToBeTravelledViewHolder, position: Int) {

        holder.bindData(context, pairList[position].first, pairList[position].second)
    }

    override fun getItemCount(): Int {
         return pairList.size

    }
}