package com.ateam.travelguide.presentation.ui.activity

import android.content.Context
import androidx.lifecycle.ViewModel
import com.ateam.travelguide.database.ImageOperation
import com.ateam.travelguide.database.LocationOperation
import com.ateam.travelguide.model.Image
import com.ateam.travelguide.model.Location

class MainViewModel: ViewModel() {

    fun getFirstImage(context : Context, locationId : Int) : Image? {
        val imageList = ImageOperation(context).getAllImage(locationId)
        return if(imageList.isNotEmpty())
            imageList.first()
        else null
    }

    fun getAllLocation(context: Context, visitStatus: Boolean) : ArrayList<Location> {
        return LocationOperation(context).getAllLocation(visitStatus)
    }

}