package com.ateam.travelguide.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.ateam.travelguide.util.Constant.IMAGE_TABLE
import com.ateam.travelguide.util.Constant.LOCATION_TABLE
import com.ateam.travelguide.util.Constant.VISIT_HISTORY_TABLE

class DatabaseOpenHelper(context: Context, name: String, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version){
    override fun onCreate(db : SQLiteDatabase) {

        val locationTable =
            "CREATE TABLE $LOCATION_TABLE(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "location_name TEXT NOT NULL, location_date TEXT, location_short_description TEXT, location_long_description TEXT, location_priority INTEGER, " +
                    "location_visit_status BOOLEAN, location_latitude TEXT NOT NULL, location_longitude TEXT NOT NULL)"

        val imageTable =
            "CREATE TABLE $IMAGE_TABLE(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, image_uri " +
                    "TEXT, image_location_id INTEGER REFERENCES $LOCATION_TABLE(id))"

        val visitHistoryTable =
            "CREATE TABLE $VISIT_HISTORY_TABLE(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "year INTEGER, month INTEGER, day INTEGER, history_location_id INTEGER REFERENCES $LOCATION_TABLE(id))"

        db.execSQL(locationTable)
        db.execSQL(imageTable)
        db.execSQL(visitHistoryTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $LOCATION_TABLE")
        db.execSQL("DROP TABLE IF EXISTS $IMAGE_TABLE")
        db.execSQL("DROP TABLE IF EXISTS $VISIT_HISTORY_TABLE")
        onCreate(db)
    }
}