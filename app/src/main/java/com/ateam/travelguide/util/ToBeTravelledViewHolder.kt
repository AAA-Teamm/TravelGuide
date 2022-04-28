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


class ToBeTravelledViewHolder (itemView : View, itemClick : ((position : Int)->Unit)) : RecyclerView.ViewHolder(itemView){

    var ivLocationPicture : ImageView
    var tvLocationName: TextView
    var tvShortDetail: TextView
    var tvLongDetail : TextView
    var ivImportance : ImageView


    init {
        tvLocationName = itemView.findViewById(R.id.tvLocationName)
        tvShortDetail = itemView.findViewById(R.id.tvShortDetail)
        tvLongDetail = itemView.findViewById(R.id.tvLongDetail)
        ivLocationPicture = itemView.findViewById(R.id.ivLocationPicture)
        ivImportance = itemView.findViewById(R.id.ivImportance)

        itemView.setOnClickListener { itemClick(adapterPosition) }
    }





    fun bindData(context : Context, location: Location, image: Image?)

    {
        tvLocationName.text = location.name
        tvShortDetail.text = location.shortDescription
        tvLongDetail.text = location.longDescription

        ivImportance.setImageResource(R.drawable.circle_background_blue)

        when (location.priority)
        {
            0 -> ivImportance.setImageResource(R.drawable.circle_background_green)
            1 -> ivImportance.setImageResource(R.drawable.circle_background_blue)
            2 ->ivImportance.setImageResource(R.drawable.circle_background_gray)
        }




        if(image == null) ivLocationPicture.setImageResource(R.drawable.union)

        else ivLocationPicture.setImageURI(image.uri!!.toUri())



    }



}