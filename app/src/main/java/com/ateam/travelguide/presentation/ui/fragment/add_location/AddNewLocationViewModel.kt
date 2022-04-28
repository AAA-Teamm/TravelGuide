package com.ateam.travelguide.presentation.ui.fragment.add_location

import android.content.Context
import androidx.lifecycle.ViewModel
import com.ateam.travelguide.database.ImageOperation
import com.ateam.travelguide.database.LocationOperation
import com.ateam.travelguide.model.Image
import com.ateam.travelguide.model.Location

class AddNewLocationViewModel : ViewModel(){

    fun addNewLocation(context : Context, location : Location){
        LocationOperation(context).addLocation(location)
    }

    fun addImages(context : Context, imageList : ArrayList<Image>){
        for(img in imageList){
            ImageOperation(context).addImage(img)
        }
    }

}