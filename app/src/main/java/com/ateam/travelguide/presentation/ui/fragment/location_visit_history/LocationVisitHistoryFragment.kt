package com.ateam.travelguide.presentation.ui.fragment.location_visit_history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ateam.travelguide.R
import com.ateam.travelguide.databinding.FragmentLocationVisitHistoryBinding
import com.ateam.travelguide.presentation.adapter.VisitHistoryImageListAdapter
import com.ateam.travelguide.util.VisitHistoryImagesClickListener

class LocationVisitHistoryFragment : Fragment(), VisitHistoryImagesClickListener {

    private var _binding: FragmentLocationVisitHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: VisitHistoryImageListAdapter
    private lateinit var imageList: ArrayList<Int>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLocationVisitHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // todo "we will be update this line"
        binding.apply {
            toolbarTitle.text = "Konum AdiX"
        }

        // todo "we will be delete mock data"
        // todo "dont forget to give the data list as URI"
        imageList = ArrayList()
        imageList.add(R.drawable.ic_launcher_background)
        imageList.add(R.drawable.ic_launcher_foreground)
        imageList.add(R.drawable.ic_launcher_background)
        imageList.add(R.drawable.ic_launcher_foreground)
        imageList.add(R.drawable.ic_launcher_background)
        imageList.add(R.drawable.ic_launcher_foreground)
        imageList.add(R.drawable.ic_launcher_background)
        imageList.add(R.drawable.ic_launcher_foreground)
        imageList.add(-1)

        adapter = VisitHistoryImageListAdapter(this)
        adapter.imageList = imageList
        binding.recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickedDeleteButton() {
        // todo "we will be update this line"
        Toast.makeText(requireContext(), "onClickedDeleteButton", Toast.LENGTH_SHORT).show()
    }

    override fun onClickAddNewPhoto() {
        // todo "we will be update this line"
        Toast.makeText(requireContext(), "onClickAddNewPhoto", Toast.LENGTH_SHORT).show()
    }

}