package com.ateam.travelguide.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ateam.travelguide.R
import com.ateam.travelguide.databinding.AddImageRowBinding
import com.ateam.travelguide.databinding.AddImageRowWithDeleteButtonBinding
import com.ateam.travelguide.util.VisitHistoryImagesClickListener
import com.ateam.travelguide.util.VisitHistoryViewHolder

class VisitHistoryImageListAdapter(
    private val clickListener: VisitHistoryImagesClickListener,
) : RecyclerView.Adapter<VisitHistoryViewHolder>() {

    // todo "dont forget to give the data list as URI"
    private val diffUtil = object : DiffUtil.ItemCallback<Int>() {
        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }
    }

    private var recyclerDiffUtil = AsyncListDiffer(this, diffUtil)

    var imageList: List<Int>
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
                imageList[position],
                clickListener
            )
        }
    }

    override fun getItemCount(): Int = imageList.size

    override fun getItemViewType(position: Int): Int {
        return when (imageList[position]) {
            -1 -> R.layout.add_image_row
            else -> R.layout.add_image_row_with_delete_button
        }
    }

}