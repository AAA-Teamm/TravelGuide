package com.ateam.travelguide.util

object Constant {

    const val DATABASE_NAME = "TravelGuideDatabase"
    const val LOCATION_TABLE = "location_table"
    const val IMAGE_TABLE = "image_table"
    const val VISIT_HISTORY_TABLE = "visit_history_table"
    const val ID = "id"

    // Location table column titles
    const val LOCATION_NAME = "location_name"
    const val LOCATION_DATE = "location_date"
    const val LOCATION_SHORT_DESCRIPTION = "location_short_description"
    const val LOCATION_LONG_DESCRIPTION = "location_long_description"
    const val LOCATION_PRIORITY = "location_priority"
    const val LOCATION_VISIT_STATUS = "location_visit_status"
    const val LOCATION_LATITUDE = "location_latitude"
    const val LOCATION_LONGITUDE = "location_longitude"

    // Image table column titles
    const val IMAGE_URI = "image_uri"
    const val IMAGE_LOCATION_ID = "image_location_id"

    // Visit History table column titles
    const val YEAR = "history_year"
    const val MONTH = "history_month"
    const val DAY = "history_day"
    const val HISTORY_LONG_DESCRIPTION = "long_description"
    const val HISTORY_LOCATION_ID = "history_location_id"

}