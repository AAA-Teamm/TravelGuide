package com.ateam.travelguide.presentation.ui.fragment.location_visit_history

import android.content.Context
import androidx.lifecycle.ViewModel
import com.ateam.travelguide.database.ImageOperation
import com.ateam.travelguide.database.LocationOperation
import com.ateam.travelguide.database.VisitHistoryOperation
import com.ateam.travelguide.model.Image
import com.ateam.travelguide.model.Location
import com.ateam.travelguide.model.VisitHistory

class LocationVisitHistoryViewModel : ViewModel() {

    fun addVisitHistory(context: Context, visitHistory: VisitHistory) {
        VisitHistoryOperation(context).addVisitHistory(visitHistory)
    }

    fun getAllImage(context: Context, locationId: Int): ArrayList<Image?> {
        return ImageOperation(context).getAllImage(locationId)
    }

    fun addNewImagesToDatabase(context: Context, image: Image) {
        ImageOperation(context).addImage(image)
    }

    fun getLocationInfo(context: Context, locationId: Int): Location {
        return LocationOperation(context).getSelectedLocation(locationId)
    }

    fun deleteAllLocationImages(context: Context, locationId: Int) {
        ImageOperation(context).deleteLocationImages(locationId)
    }

}