package com.ateam.travelguide.util

import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.ateam.travelguide.databinding.AddImageRowBinding
import com.ateam.travelguide.databinding.AddImageRowWithDeleteButtonBinding
import com.ateam.travelguide.model.Image

sealed class VisitHistoryViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    class AddImageViewHolder(private val binding: AddImageRowBinding) :
        VisitHistoryViewHolder(binding) {
        fun bind(clickListener: VisitHistoryImagesClickListener) {
            binding.cardView.setOnClickListener { clickListener.onClickAddNewPhoto() }
        }
    }

    class AddImageWithDeleteButtonViewHolder(private val binding: AddImageRowWithDeleteButtonBinding) :
        VisitHistoryViewHolder(binding) {
        fun bind(imageUri: Image, position: Int, clickListener: VisitHistoryImagesClickListener) {
            binding.imageViewDelete.setOnClickListener {
                clickListener.onClickedDeleteButton(position)
            }
            binding.imageViewPhoto.setImageURI(Uri.parse(imageUri.uri))
        }
    }

}
