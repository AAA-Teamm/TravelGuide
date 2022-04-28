package com.ateam.travelguide.presentation.ui.fragment.location_detail

import android.content.Context
import androidx.lifecycle.ViewModel
import com.ateam.travelguide.database.ImageOperation
import com.ateam.travelguide.database.LocationOperation
import com.ateam.travelguide.database.VisitHistoryOperation
import com.ateam.travelguide.model.Image
import com.ateam.travelguide.model.Location
import com.ateam.travelguide.model.VisitHistory

class LocationDetailViewModel : ViewModel() {

    fun getLocationInfo(context: Context, id: Int): Location? {
        return LocationOperation(context).getSelectedLocation(id)
    }

    fun getAllVisitHistoryForSelectedLocation(context: Context, id: Int): ArrayList<VisitHistory?> {
        return VisitHistoryOperation(context).getAllVisitHistory(id)
    }

    fun getImageListForSelectedLocation(context: Context, id: Int): ArrayList<Image?> {
        return ImageOperation(context).getAllImage(id)
    }

}