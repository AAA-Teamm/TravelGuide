package com.ateam.travelguide.presentation.ui.fragment.tab_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ateam.travelguide.databinding.FragmentToBeTravelledBinding
import com.ateam.travelguide.model.Image
import com.ateam.travelguide.model.Location
import com.ateam.travelguide.presentation.adapter.ToBeTravelledAdapter
import com.ateam.travelguide.presentation.ui.activity.MainViewModel

class ToBeTravelled : Fragment() {

    lateinit var binding: FragmentToBeTravelledBinding
    private lateinit var adapter: ToBeTravelledAdapter
    private lateinit var viewModel: MainViewModel

    var locationList = ArrayList<Location>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentToBeTravelledBinding.inflate(inflater)


        val lm = LinearLayoutManager(requireActivity())
        lm.orientation = LinearLayoutManager.VERTICAL
        binding.rvToBeTravelled.layoutManager = lm


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

        adapter = ToBeTravelledAdapter(requireContext(), pairlist, ::itemClick)
        binding.rvToBeTravelled.adapter = adapter
    }

    fun itemClick(position : Int)
    {
        Toast.makeText(requireActivity(), "Tıklandı", Toast.LENGTH_SHORT).show()
    }


    }





