package com.ateam.travelguide.util

import com.ateam.travelguide.R

data class Priority( val id: Int, val image : Int, val priorityName : String)

class PriorityUtil {

    private val priorityId = intArrayOf(
        0,
        1,
        2
    )

    private val images = intArrayOf(
        R.color.green_circle_image,
        R.color.blue_circle_image,
        R.color.gray_circle_image
    )

    private val priorities = arrayOf(
        "Öncelik 1",
        "Öncelik 2",
        "Öncelik 3"
    )

    var list: ArrayList<Priority>? = null
        get() {

            if (field != null)
                return field

            field = ArrayList()
            for (i in images.indices) {

                val priorityId = priorityId[i]
                val imageId = images[i]
                val priorityName = priorities[i]

                val priority = Priority(priorityId, imageId, priorityName)
                field!!.add(priority)
            }

            return field
        }
}