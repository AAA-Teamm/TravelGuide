package com.ateam.travelguide.presentation.ui.fragment.tab_fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ateam.travelguide.databinding.FragmentToBeTravelledBinding
import com.ateam.travelguide.model.Image
import com.ateam.travelguide.model.Location
import com.ateam.travelguide.presentation.adapter.ToBeTravelledAdapter
import com.ateam.travelguide.presentation.ui.activity.LocationActivity
import com.ateam.travelguide.presentation.ui.activity.MainViewModel
import com.ateam.travelguide.util.Constant

class ToBeTravelled : Fragment() {

    lateinit var binding: FragmentToBeTravelledBinding
    private lateinit var adapter: ToBeTravelledAdapter
    private lateinit var viewModel: MainViewModel
    private lateinit var locationList: ArrayList<Location>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentToBeTravelledBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        locationList = viewModel.getAllLocation(requireContext(), false)

        val pairlist = ArrayList<Pair<Location, Image?>>()

        for (location in locationList) {
            val image = viewModel.getFirstImage(requireContext(), location.id)
            image?.let {
                println(it.uri)
                pairlist.add(Pair(location, it))
            }
        }

        adapter = ToBeTravelledAdapter(requireContext(), pairlist, ::itemClick)
        binding.rvToBeTravelled.adapter = adapter

    }

    override fun onResume() {
        super.onResume()

        locationList = viewModel.getAllLocation(requireContext(), false)

        val pairlist = ArrayList<Pair<Location, Image?>>()

        for (location in locationList) {

            val image = viewModel.getFirstImage(requireContext(), location.id)


            pairlist.add(Pair(location, image))
        }

        adapter = ToBeTravelledAdapter(requireContext(), pairlist, ::itemClick)
        binding.rvToBeTravelled.adapter = adapter
    }

    private fun itemClick(position: Int) {
        val intent = Intent(requireActivity(), LocationActivity::class.java)
        intent.putExtra(Constant.LOCATION_ID, locationList[position].id)
        requireActivity().startActivity(intent)
    }

}





