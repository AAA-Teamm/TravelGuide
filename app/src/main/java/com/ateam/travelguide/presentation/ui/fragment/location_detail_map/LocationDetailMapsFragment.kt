package com.ateam.travelguide.presentation.ui.fragment.location_detail_map

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.ateam.travelguide.R
import com.ateam.travelguide.databinding.FragmentLocationDetailMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class LocationDetailMapsFragment : Fragment() {

    private var _binding: FragmentLocationDetailMapsBinding? = null
    private val binding get() = _binding!!
    private val args: LocationDetailMapsFragmentArgs by navArgs()
    private lateinit var viewModel: LocationDetailMapsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLocationDetailMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        viewModel = ViewModelProvider(requireActivity())[LocationDetailMapsViewModel::class.java]
        val locationId = args.locationId

        val locationInfo = viewModel.locationInfo(requireContext(), locationId)!!

        val callback = OnMapReadyCallback { googleMap ->
            val sydney = LatLng(locationInfo.latitude.toDouble(), locationInfo.longitude.toDouble())
            googleMap.addMarker(MarkerOptions().position(sydney).title(locationInfo.name))
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15f))
        }

        mapFragment?.getMapAsync(callback)

        binding.toolbarTitle.text = locationInfo.name
        binding.buttonGo.setOnClickListener {
            openMaps(locationInfo.latitude, locationInfo.longitude)
        }
    }

    private fun openMaps(latitude: String, longitude: String) {
        val gmmIntentUri = Uri.parse("geo:$latitude,$longitude")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        mapIntent.resolveActivity(requireActivity().packageManager)?.let {
            startActivity(mapIntent)
        }
    }

}