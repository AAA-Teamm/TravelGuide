package com.ateam.travelguide.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ateam.travelguide.R
import com.ateam.travelguide.databinding.AddImageRowBinding
import com.ateam.travelguide.databinding.AddImageRowWithDeleteButtonBinding
import com.ateam.travelguide.model.Image
import com.ateam.travelguide.util.Constant.NO_IMAGE_FEATURE
import com.ateam.travelguide.util.VisitHistoryImagesClickListener
import com.ateam.travelguide.util.VisitHistoryViewHolder

class VisitHistoryImageListAdapter(
    private val clickListener: VisitHistoryImagesClickListener,
) : RecyclerView.Adapter<VisitHistoryViewHolder>() {

    private val diffUtil = object : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem == newItem
        }
    }

    private var recyclerDiffUtil = AsyncListDiffer(this, diffUtil)

    var imageList: List<Image?>
        get() = recyclerDiffUtil.currentList
        set(value) = recyclerDiffUtil.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VisitHistoryViewHolder {
        return when (viewType) {
            R.layout.add_image_row -> VisitHistoryViewHolder.AddImageViewHolder(
                AddImageRowBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            R.layout.add_image_row_with_delete_button -> VisitHistoryViewHolder.AddImageWithDeleteButtonViewHolder(
                AddImageRowWithDeleteButtonBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException("Invalid ViewType Provided")
        }
    }

    override fun onBindViewHolder(holder: VisitHistoryViewHolder, position: Int) {
        when (holder) {
            is VisitHistoryViewHolder.AddImageViewHolder -> holder.bind(
                clickListener
            )
            is VisitHistoryViewHolder.AddImageWithDeleteButtonViewHolder -> holder.bind(
                imageList[position]!!,
                clickListener,
                position
            )
        }
    }

    override fun getItemCount(): Int = imageList.size

    override fun getItemViewType(position: Int): Int {
        return when (imageList[position]!!.uri) {
            NO_IMAGE_FEATURE -> R.layout.add_image_row
            else -> R.layout.add_image_row_with_delete_button
        }
    }

}