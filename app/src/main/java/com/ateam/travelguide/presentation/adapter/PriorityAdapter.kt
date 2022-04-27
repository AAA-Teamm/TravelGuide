package com.ateam.travelguide.presentation.adapter


import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.ateam.travelguide.databinding.SpinnerItemBinding
import com.ateam.travelguide.util.Priority


class PriorityAdapter(context : Context, val priorityList : ArrayList<Priority>) : ArrayAdapter<Priority>(context,0, priorityList){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View{
        val priority = priorityList[position]
        val binding = SpinnerItemBinding.inflate(LayoutInflater.from(context), parent, false)

        binding.priority.text = priority.priorityName

        var buttonDrawable: Drawable? = binding.imagePriority.background
        buttonDrawable = DrawableCompat.wrap(buttonDrawable!!)

        when (priority.id) {
            0 -> {
                DrawableCompat.setTint(
                    buttonDrawable,
                    ContextCompat.getColor(context, priority.image)
                )
            }
            1 -> {
                DrawableCompat.setTint(
                    buttonDrawable,
                    ContextCompat.getColor(context, priority.image)
                )
            }
            2 -> {
                DrawableCompat.setTint(
                    buttonDrawable,
                    ContextCompat.getColor(context, priority.image)
                )
            }
        }
        binding.imagePriority.background = buttonDrawable

        return binding.imagePriority
    }
}