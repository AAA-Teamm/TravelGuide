package com.ateam.travelguide.presentation.ui.fragment.location_detail

import android.content.Context
import androidx.lifecycle.ViewModel
import com.ateam.travelguide.model.Location
import com.ateam.travelguide.model.VisitHistory

class LocationDetailViewModel : ViewModel() {

    fun getLocationInfo(context: Context, id: Int): Location? {
        val locationInfo = Location(
            0,
            "Adem\nAtici",
            "12.12.1222",
            "Short Desc",
            "long desc",
            2,
            false,
            "12",
            "12"
        )
        // todo "we will use this line for location info"
        //return LocationOperation(context).getSelectedLocation(id)
        return locationInfo
    }

    fun getAllVisitHistoryForSelectedLocation(context: Context, id: Int): ArrayList<VisitHistory> {
        // todo "we will be delete mock list"
        val visitHistoryList = arrayListOf(
            VisitHistory(0, 2022, 12, 1, "long desc", 1),
            VisitHistory(0, 2022, 12, 1, "long desc", 1),
            VisitHistory(0, 2022, 12, 1, "long desc", 1),
            VisitHistory(0, 2022, 12, 1, "long desc", 1),
            VisitHistory(0, 2022, 12, 1, "long desc", 1)
        )

        // todo "we will use this line for visitHistory list"
        //return VisitHistoryOperation(context).getAllVisitHistory(id)
        return visitHistoryList
    }

}