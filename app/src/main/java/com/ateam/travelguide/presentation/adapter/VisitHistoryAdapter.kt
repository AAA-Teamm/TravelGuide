package com.ateam.travelguide.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ateam.travelguide.databinding.VisitHistoryRowBinding
import com.ateam.travelguide.model.VisitHistory
import com.ateam.travelguide.util.writeDate

class VisitHistoryAdapter : RecyclerView.Adapter<VisitHistoryAdapter.VisitHistoryViewHolder>() {

    private val diffUtil = object : DiffUtil.ItemCallback<VisitHistory>() {
        override fun areItemsTheSame(oldItem: VisitHistory, newItem: VisitHistory): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: VisitHistory, newItem: VisitHistory): Boolean {
            return oldItem == newItem
        }
    }

    private var recyclerDiffUtil = AsyncListDiffer(this, diffUtil)

    var visitHistoryList: List<VisitHistory?>
        get() = recyclerDiffUtil.currentList
        set(value) = recyclerDiffUtil.submitList(value)

    class VisitHistoryViewHolder(val binding: VisitHistoryRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VisitHistoryViewHolder {
        val binding =
            VisitHistoryRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VisitHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VisitHistoryViewHolder, position: Int) {
        val currentItem = visitHistoryList[position]
        holder.binding.textViewDate.text = String().writeDate(currentItem!!.day!!, currentItem.month!!, currentItem.year!!)
        holder.binding.textViewLongDesc.text = currentItem.longDescription
    }

    override fun getItemCount(): Int = visitHistoryList.size

}