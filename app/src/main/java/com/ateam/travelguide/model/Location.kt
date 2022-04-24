package com.ateam.travelguide.model

data class Location(
    val id: Int = 0,
    val name: String?,
    val date: String?,
    val shortDescription: String?,
    val longDescription: String?,
    val priority: Int?,
    val visitStatus: Boolean = false,
    val latitude: String,
    val longitude: String
)
