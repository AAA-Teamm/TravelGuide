package com.ateam.travelguide.model

data class Image(
    val id: Int = 0,
    val uri: String?,
    val year: Int?,
    val month: Int?,
    val day: Int?,
    val locationId: Int
)