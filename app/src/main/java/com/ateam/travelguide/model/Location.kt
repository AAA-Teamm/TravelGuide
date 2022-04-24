package com.ateam.travelguide.model

class Location(
    val id: Int = 0,
    val locationName: String?,
    val locationDate: String?,
    val locationShortDescription: String?,
    val locationLongDescription: String?,
    val locationPriority: Int?,
    val locationVisitStatus: Boolean?,
    val locationLatitude: String,
    val locationLongitude: String
)
