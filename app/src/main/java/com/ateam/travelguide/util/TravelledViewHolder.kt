package com.ateam.travelguide.util

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.ateam.travelguide.R
import com.ateam.travelguide.model.Image
import com.ateam.travelguide.model.Location

class TravelledViewHolder (itemView: View, itemClick : ((position : Int)->Unit)) : RecyclerView.ViewHolder(itemView){

    var ivLocationPicture : ImageView
    var tvLocationName: TextView
    var tvShortDetail: TextView
    var tvLongDetail : TextView
    var tvDate : TextView

    init
    {
        tvLocationName = itemView.findViewById(R.id.tvLocationName)
        tvShortDetail = itemView.findViewById(R.id.tvShortDetail)
        tvLongDetail = itemView.findViewById(R.id.tvLongDetail)
        ivLocationPicture = itemView.findViewById(R.id.ivLocationPicture)
        tvDate = itemView.findViewById(R.id.tvDate)

        itemView.setOnClickListener { itemClick(adapterPosition) }
    }

    fun bindData(context : Context, location: Location, image: Image?)

    {
        tvLocationName.text = location.name
        tvShortDetail.text = location.shortDescription
        tvLongDetail.text = location.longDescription
        tvDate.text = location.date

        if(image == null) ivLocationPicture.setImageResource(R.drawable.union)
        else ivLocationPicture.setImageURI(image.uri!!.toUri())



    }


}