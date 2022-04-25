package com.ateam.travelguide.presentation.ui.fragment.location_detail_map

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    private val callback = OnMapReadyCallback { googleMap ->
        val sydney = LatLng(40.765506, 29.940703)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in New York"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15f))
    }

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
        mapFragment?.getMapAsync(callback)

        binding.toolbarTitle.text = "Yer Adi"
    }

}