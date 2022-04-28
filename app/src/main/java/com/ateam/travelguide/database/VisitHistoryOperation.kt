package com.ateam.travelguide.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.core.database.getIntOrNull
import androidx.core.database.getStringOrNull
import com.ateam.travelguide.model.VisitHistory
import com.ateam.travelguide.util.Constant.DATABASE_NAME
import com.ateam.travelguide.util.Constant.HISTORY_DAY
import com.ateam.travelguide.util.Constant.HISTORY_LOCATION_ID
import com.ateam.travelguide.util.Constant.HISTORY_LONG_DESCRIPTION
import com.ateam.travelguide.util.Constant.HISTORY_MONTH
import com.ateam.travelguide.util.Constant.HISTORY_YEAR
import com.ateam.travelguide.util.Constant.VISIT_HISTORY_TABLE

class VisitHistoryOperation(context: Context) {

    private var travelGuideDatabase: SQLiteDatabase? = null
    private var dbOpenHelper: DatabaseOpenHelper = DatabaseOpenHelper(context,
        DATABASE_NAME, null, 1)

    private fun open() {
        travelGuideDatabase = dbOpenHelper.writableDatabase
    }

    private fun close() {
        if (travelGuideDatabase != null && travelGuideDatabase!!.isOpen) {
            travelGuideDatabase!!.close()
        }
    }

    fun addVisitHistory(visitHistory : VisitHistory) {
        val cv = ContentValues()
        cv.put(HISTORY_YEAR, visitHistory.year)
        cv.put(HISTORY_MONTH, visitHistory.month)
        cv.put(HISTORY_DAY, visitHistory.day)
        cv.put(HISTORY_LONG_DESCRIPTION, visitHistory.longDescription)
        cv.put(HISTORY_LOCATION_ID, visitHistory.historyLocationId)

        open()
        travelGuideDatabase!!.insert(VISIT_HISTORY_TABLE, null, cv)
        close()
    }

    private fun getAllVisitHistoryQuery(locationId : Int): Cursor {
        val query = "SELECT * FROM $VISIT_HISTORY_TABLE WHERE $HISTORY_LOCATION_ID = ?"
        return travelGuideDatabase!!.rawQuery(query, arrayOf(locationId.toString()))
    }

    @SuppressLint("Range")
    fun getAllVisitHistory(locationId : Int): ArrayList<VisitHistory?> {
        val visitHistoryList = ArrayList<VisitHistory?>()

        open()
        val c: Cursor = getAllVisitHistoryQuery(locationId)

        if (c.moveToFirst()) {
            do {
                val visitHistory = VisitHistory(
                    id = c.getInt(0),
                    year = c.getIntOrNull(c.getColumnIndex(HISTORY_YEAR)),
                    month = c.getIntOrNull(c.getColumnIndex(HISTORY_MONTH)),
                    day = c.getIntOrNull(c.getColumnIndex(HISTORY_DAY)),
                    longDescription = c.getStringOrNull(c.getColumnIndex(HISTORY_LONG_DESCRIPTION)),
                    historyLocationId = c.getInt(c.getColumnIndex(HISTORY_LOCATION_ID))
                )
                visitHistoryList.add(visitHistory)
            } while (c.moveToNext())
        }

        close()
        return visitHistoryList
    }
}