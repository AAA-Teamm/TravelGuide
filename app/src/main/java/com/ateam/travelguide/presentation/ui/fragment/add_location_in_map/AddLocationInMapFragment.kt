package com.ateam.travelguide.presentation.ui.fragment.add_location_in_map

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ateam.travelguide.R
import com.ateam.travelguide.databinding.FragmentAddLocationInMapBinding
import com.ateam.travelguide.presentation.ui.activity.AddActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class AddLocationInMapFragment : Fragment() {

    private var _binding: FragmentAddLocationInMapBinding? = null
    private val binding get() = _binding!!
    private lateinit var mMap: GoogleMap
    private lateinit var selectedMapCoordinate: LatLng
    private var marker : Marker? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddLocationInMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        connectToMap()
        initClickListeners()
    }

    fun connectToMap(){
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapInFragment) as
                SupportMapFragment?

        ActivityCompat.requestPermissions(
            (activity as AddActivity), arrayOf(
                Manifest.permission
                    .ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ), 1
        )

        val callback = OnMapReadyCallback { googleMap ->
            mMap = googleMap
            val sydney = LatLng(-34.0, 151.0)
            selectedMapCoordinate = sydney
            val sydneyMarker = googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10.0f))
            initMapClickListeners(sydneyMarker)
        }

        mapFragment?.getMapAsync(callback)
    }

    private fun initMapClickListeners(sydneyMarker : Marker?){
        mMap.setOnMapLongClickListener { latLng ->
            sydneyMarker!!.remove()
            if(marker!=null){
                marker!!.remove()
            }
            selectedMapCoordinate = latLng
            marker = mMap.addMarker(MarkerOptions().position(selectedMapCoordinate!!))
        }
    }

    private fun initClickListeners(){
        binding.apply {
            buttonSaveMap.setOnClickListener {
                val action = AddLocationInMapFragmentDirections
                    .actionAddLocationInMapFragmentToAddNewLocationFragment(selectedMapCoordinate
                        .latitude.toString(),selectedMapCoordinate.longitude.toString())
                findNavController().navigate(action)
            }

            imageViewBackNavigation.setOnClickListener {
                val action = AddLocationInMapFragmentDirections
                    .actionAddLocationInMapFragmentToAddNewLocationFragment(null, null)
                findNavController().navigate(action)
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
