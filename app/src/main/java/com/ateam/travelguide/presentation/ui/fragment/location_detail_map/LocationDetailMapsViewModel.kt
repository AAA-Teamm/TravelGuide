package com.ateam.travelguide.presentation.ui.fragment.location_detail_map

import android.content.Context
import androidx.lifecycle.ViewModel
import com.ateam.travelguide.database.LocationOperation
import com.ateam.travelguide.model.Location

class LocationDetailMapsViewModel: ViewModel() {

    fun locationInfo(context: Context, locationId: Int): Location? {
        return LocationOperation(context).getSelectedLocation(locationId)
    }

}