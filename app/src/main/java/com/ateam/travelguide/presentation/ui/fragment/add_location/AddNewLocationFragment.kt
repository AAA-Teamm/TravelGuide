package com.ateam.travelguide.presentation.ui.fragment.add_location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ateam.travelguide.R
import com.ateam.travelguide.databinding.FragmentAddNewLocationBinding
import com.ateam.travelguide.model.Image
import com.ateam.travelguide.presentation.adapter.PriorityAdapter
import com.ateam.travelguide.presentation.adapter.VisitHistoryImageListAdapter
import com.ateam.travelguide.util.PriorityUtil
import com.ateam.travelguide.util.VisitHistoryImagesClickListener
import com.google.android.gms.maps.model.LatLng

class AddNewLocationFragment : Fragment(), VisitHistoryImagesClickListener {

    private var _binding: FragmentAddNewLocationBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: VisitHistoryImageListAdapter
    private lateinit var imageList: ArrayList<Image>
    private var mapCoordinate : LatLng? = null
    private val args : AddNewLocationFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddNewLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initClickListeners()
        setupSimpleSpinner()
    }

    private fun initView() {
        imageList = ArrayList()
        adapter = VisitHistoryImageListAdapter(this)
        adapter.imageList = imageList
        binding.recyclerView.adapter = adapter
    }

    private fun initClickListeners() {
        binding.apply {

            buttonAddLocationMap.setOnClickListener {
                findNavController().navigate(R.id.action_addNewLocationFragment_to_addLocationInMapFragment)
            }

            buttonAddLocation.setOnClickListener {
                requireActivity().finish() //&save
                /* if(args.lat != null){
               //save
        }   */
            }

            imageViewBackNavigation.setOnClickListener {
                requireActivity().finish()
            }
        }
    }

    private fun setupSimpleSpinner() {
        val adapter = PriorityAdapter(
            requireContext(),
            PriorityUtil().list!!
        )

        binding.customSpinner.adapter = adapter
/*
        binding.customSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //val selectedItem = parent!!.getItemAtPosition(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }*/
    }

    override fun onClickedDeleteButton(position: Int) {
        TODO("Not yet implemented")
    }

    override fun onClickAddNewPhoto() {
        TODO("Not yet implemented")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
