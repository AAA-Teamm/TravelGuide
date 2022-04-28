package com.ateam.travelguide.presentation.ui.fragment.tab_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ateam.travelguide.R
import com.ateam.travelguide.databinding.FragmentToBeTravelledBinding
import com.ateam.travelguide.databinding.FragmentTravelledBinding
import com.ateam.travelguide.model.Image
import com.ateam.travelguide.model.Location
import com.ateam.travelguide.presentation.adapter.ToBeTravelledAdapter
import com.ateam.travelguide.presentation.adapter.TravelledAdapter
import com.ateam.travelguide.presentation.ui.activity.MainViewModel


class TravelledFragment : Fragment() {

    lateinit var binding: FragmentTravelledBinding
    private lateinit var adapter: TravelledAdapter

    private lateinit var viewModel: MainViewModel
    var locationList = ArrayList<Location>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTravelledBinding.inflate(inflater)

        val lm = LinearLayoutManager(requireActivity())
        lm.orientation = LinearLayoutManager.VERTICAL
        binding.rvTravelled.layoutManager = lm



        return binding.root

    }

    override fun onResume() {
        super.onResume()

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        locationList = viewModel.getAllLocation(requireContext(), true)


        val pairlist = ArrayList<Pair<Location, Image?>>()

        for (location in locationList) {

            val image = viewModel.getFirstImage(requireContext(), location.id)


            pairlist.add(Pair(location, image))
        }

        adapter = TravelledAdapter(requireContext(), pairlist, ::itemClick)

        binding.rvTravelled.adapter = adapter
    }

    fun itemClick(position : Int)
    {
        Toast.makeText(requireActivity(), "Tıklandı. 2.fragment", Toast.LENGTH_SHORT).show()
    }

    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        locationList = viewModel.getAllLocation(requireContext(), true)

        val pairlist = ArrayList<Pair<Location, Image?>>()

        for (location in locationList) {
            val image = viewModel.getFirstImage(requireContext(), location.id)

            pairlist.add(Pair(location, image))
        }

        adapter = TravelledAdapter(requireContext(), pairlist)

        binding.rvTravelled.adapter = adapter
    }

     */



}