package com.ateam.travelguide.model

data class VisitHistory(
    val id: Int = 0,
    val year: Int?,
    val month: Int?,
    val day: Int?,
    val longDescription: String?,
    val historyLocationId : Int
)