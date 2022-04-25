package com.ateam.travelguide.util

import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.ateam.travelguide.databinding.AddImageRowBinding
import com.ateam.travelguide.databinding.AddImageRowWithDeleteButtonBinding

sealed class VisitHistoryViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    class AddImageViewHolder(private val binding: AddImageRowBinding) :
        VisitHistoryViewHolder(binding) {
        fun bind(clickListener: VisitHistoryImagesClickListener) {
            binding.cardView.setOnClickListener { clickListener.onClickAddNewPhoto() }
        }
    }

    class AddImageWithDeleteButtonViewHolder(private val binding: AddImageRowWithDeleteButtonBinding) :
        VisitHistoryViewHolder(binding) {
        // todo "dont forget to give the data list as URI"
        fun bind(imageUri: Int, clickListener: VisitHistoryImagesClickListener) {
            binding.imageViewDelete.setOnClickListener { clickListener.onClickedDeleteButton() }
            binding.imageViewPhoto.setImageResource(imageUri)
        }
    }

}
