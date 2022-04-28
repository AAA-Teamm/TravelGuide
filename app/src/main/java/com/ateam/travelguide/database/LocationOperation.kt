package com.ateam.travelguide.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.core.database.getIntOrNull
import androidx.core.database.getStringOrNull
import com.ateam.travelguide.model.Location
import com.ateam.travelguide.util.Constant.DATABASE_NAME
import com.ateam.travelguide.util.Constant.LOCATION_DATE
import com.ateam.travelguide.util.Constant.LOCATION_LATITUDE
import com.ateam.travelguide.util.Constant.LOCATION_LONGITUDE
import com.ateam.travelguide.util.Constant.LOCATION_LONG_DESCRIPTION
import com.ateam.travelguide.util.Constant.LOCATION_NAME
import com.ateam.travelguide.util.Constant.LOCATION_PRIORITY
import com.ateam.travelguide.util.Constant.LOCATION_SHORT_DESCRIPTION
import com.ateam.travelguide.util.Constant.LOCATION_TABLE
import com.ateam.travelguide.util.Constant.LOCATION_VISIT_STATUS

class LocationOperation(context: Context) {

    private var travelGuideDatabase: SQLiteDatabase? = null
    private var dbOpenHelper: DatabaseOpenHelper = DatabaseOpenHelper(context, DATABASE_NAME, null, 1)

    private fun open() {
        travelGuideDatabase = dbOpenHelper.writableDatabase
    }

    private fun close() {
        if (travelGuideDatabase != null && travelGuideDatabase!!.isOpen) {
            travelGuideDatabase!!.close()
        }
    }

    fun addLocation(location : Location) {
        val cv = ContentValues()
        cv.put(LOCATION_NAME, location.name)
        cv.put(LOCATION_DATE, location.date)
        cv.put(LOCATION_SHORT_DESCRIPTION, location.shortDescription)
        cv.put(LOCATION_LONG_DESCRIPTION, location.longDescription)
        cv.put(LOCATION_PRIORITY, location.priority)
        cv.put(LOCATION_VISIT_STATUS, location.visitStatus)
        cv.put(LOCATION_LATITUDE, location.latitude)
        cv.put(LOCATION_LONGITUDE, location.longitude)

        open()
        travelGuideDatabase!!.insert(LOCATION_TABLE, null, cv)
        close()
    }

    private fun getAllLocationQuery(visitStatus: Boolean): Cursor {
        val visitStatusState: Int = if (visitStatus) {
            1
        } else {
            0
        }
        val query = "SELECT * FROM $LOCATION_TABLE WHERE $LOCATION_VISIT_STATUS = ?"
        return travelGuideDatabase!!.rawQuery(query, arrayOf(visitStatusState.toString()))
    }

    @SuppressLint("Range")
    fun getAllLocation(visitStatus: Boolean): ArrayList<Location> {
        val locationList = ArrayList<Location>()

        open()
        val c: Cursor = getAllLocationQuery(visitStatus)

        if (c.moveToFirst()) {
            do {
                val location = Location(
                    id = c.getInt(0),
                    name = c.getStringOrNull(c.getColumnIndex(LOCATION_NAME)),
                    date = c.getStringOrNull(c.getColumnIndex(LOCATION_DATE)),
                    shortDescription = c.getStringOrNull(c.getColumnIndex(LOCATION_SHORT_DESCRIPTION)),
                    longDescription = c.getStringOrNull(c.getColumnIndex(LOCATION_LONG_DESCRIPTION)),
                    priority = c.getIntOrNull(c.getColumnIndex(LOCATION_PRIORITY)),
                    visitStatus = c.getString(c.getColumnIndex(LOCATION_VISIT_STATUS)).equals("True"),
                    latitude = c.getString(c.getColumnIndex(LOCATION_LATITUDE)),
                    longitude = c.getString(c.getColumnIndex(LOCATION_LONGITUDE))
                )
                locationList.add(location)
            } while (c.moveToNext())
        }

        close()
        return locationList
    }

    private fun getNumOfLocations(): Cursor {
        val query = "SELECT * FROM $LOCATION_TABLE"
        return travelGuideDatabase!!.rawQuery(query, null)
    }

    @SuppressLint("Range")
    fun getNumOfAllLocation(): ArrayList<Location> {
        val locationList = ArrayList<Location>()

        open()
        val c: Cursor = getNumOfLocations()

        if (c.moveToFirst()) {
            do {
                val location = Location(
                    id = c.getInt(0),
                    name = c.getStringOrNull(c.getColumnIndex(LOCATION_NAME)),
                    date = c.getStringOrNull(c.getColumnIndex(LOCATION_DATE)),
                    shortDescription = c.getStringOrNull(c.getColumnIndex(LOCATION_SHORT_DESCRIPTION)),
                    longDescription = c.getStringOrNull(c.getColumnIndex(LOCATION_LONG_DESCRIPTION)),
                    priority = c.getIntOrNull(c.getColumnIndex(LOCATION_PRIORITY)),
                    visitStatus = c.getString(c.getColumnIndex(LOCATION_VISIT_STATUS)).equals("True"),
                    latitude = c.getString(c.getColumnIndex(LOCATION_LATITUDE)),
                    longitude = c.getString(c.getColumnIndex(LOCATION_LONGITUDE))
                )
                locationList.add(location)
            } while (c.moveToNext())
        }

        close()
        return locationList
    }

    private fun getSelectedLocatinQuery(id: Int): Cursor {
        val query = "SELECT * FROM $LOCATION_TABLE WHERE id = ?"
        return travelGuideDatabase!!.rawQuery(query, arrayOf(id.toString()))
    }

    @SuppressLint("Range")
    fun getSelectedLocation(id: Int): Location {
        var location: Location? = null
        open()
        val c: Cursor = getSelectedLocatinQuery(id)
        while (c.moveToNext()) {
            location = Location(
                id = c.getInt(0),
                name = c.getStringOrNull(c.getColumnIndex(LOCATION_NAME)),
                date = c.getStringOrNull(c.getColumnIndex(LOCATION_DATE)),
                shortDescription = c.getStringOrNull(c.getColumnIndex(LOCATION_SHORT_DESCRIPTION)),
                longDescription = c.getStringOrNull(c.getColumnIndex(LOCATION_LONG_DESCRIPTION)),
                priority = c.getIntOrNull(c.getColumnIndex(LOCATION_PRIORITY)),
                visitStatus = c.getString(c.getColumnIndex(LOCATION_VISIT_STATUS)).equals("True"),
                latitude = c.getString(c.getColumnIndex(LOCATION_LATITUDE)),
                longitude = c.getString(c.getColumnIndex(LOCATION_LONGITUDE))
            )
        }

        close()
        return location!!
    }

}