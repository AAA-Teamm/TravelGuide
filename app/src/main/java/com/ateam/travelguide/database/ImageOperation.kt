package com.ateam.travelguide.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.core.database.getStringOrNull
import com.ateam.travelguide.model.Image
import com.ateam.travelguide.util.Constant.DATABASE_NAME
import com.ateam.travelguide.util.Constant.ID
import com.ateam.travelguide.util.Constant.IMAGE_LOCATION_ID
import com.ateam.travelguide.util.Constant.IMAGE_TABLE
import com.ateam.travelguide.util.Constant.IMAGE_URI

class ImageOperation(context: Context) {
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
    fun addImage(image : Image) {
        val cv = ContentValues()
        cv.put(IMAGE_URI, image.uri)
        cv.put(IMAGE_LOCATION_ID, image.locationId)

        open()
        travelGuideDatabase!!.insert(IMAGE_TABLE, null, cv)
        close()
    }
    private fun getAllImageQuery(locationId: Int): Cursor { //for LocationDetailFragment
        val query = "SELECT * FROM $IMAGE_TABLE WHERE $IMAGE_LOCATION_ID = ?"
        return travelGuideDatabase!!.rawQuery(query, arrayOf(locationId.toString()))
    }

    @SuppressLint("Range")
    fun getAllImage(locationId: Int): ArrayList<Image> {
        val imageList = ArrayList<Image>()

        open()
        val c: Cursor = getAllImageQuery(locationId)

        if (c.moveToFirst()) {
            do {
                val image = Image(
                    id = c.getInt(0),
                    uri = c.getStringOrNull(c.getColumnIndex(IMAGE_URI)),
                    locationId = c.getInt(c.getColumnIndex(IMAGE_LOCATION_ID))
                )
                imageList.add(image)
            } while (c.moveToNext())
        }

        close()
        return imageList
    }
    private fun getFirstImageQuery(): Cursor { //get first
        val query = "SELECT * FROM $IMAGE_TABLE WHERE $ID =0"
        return travelGuideDatabase!!.rawQuery(query,null)
    }

    @SuppressLint("Range")
    fun getFirstImage(): Image? {
        var image: Image? = null
        open()
        val c: Cursor = getFirstImageQuery()
        while (c.moveToNext()) {
            image = Image(
                id = c.getInt(0),
                uri = c.getStringOrNull(c.getColumnIndex(IMAGE_URI)),
                locationId = c.getInt(c.getColumnIndex(IMAGE_LOCATION_ID))
            )
        }
        close()
        return image
    }
}