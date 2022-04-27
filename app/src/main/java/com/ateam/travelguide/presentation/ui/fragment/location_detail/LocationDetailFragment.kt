package com.ateam.travelguide.presentation.ui.fragment.location_detail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ateam.travelguide.R
import com.ateam.travelguide.databinding.FragmentLocationDetailBinding
import com.ateam.travelguide.model.Image
import com.ateam.travelguide.model.Location
import com.ateam.travelguide.model.VisitHistory
import com.ateam.travelguide.presentation.adapter.VisitHistoryAdapter
import com.ateam.travelguide.util.Constant.LOCATION_ID
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel

class LocationDetailFragment : Fragment() {

    private var _binding: FragmentLocationDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: VisitHistoryAdapter
    private lateinit var viewModel: LocationDetailViewModel
    private var locationInfo: Location? = null
    private var locationVisitHistory: ArrayList<VisitHistory?> = ArrayList()
    private var locationImages: ArrayList<Image?> = ArrayList()
    private var locationId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLocationDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initClickListeners()
        initUi()
        initVisitHistoryRecycler()
    }

    private fun initView() {
        adapter = VisitHistoryAdapter()
        viewModel = ViewModelProvider(this).get(LocationDetailViewModel::class.java)
        locationId = requireActivity().intent.getIntExtra(LOCATION_ID, 0)
    }

    private fun initClickListeners() {
        binding.apply {
            buttonAddVisit.setOnClickListener {
                val action = LocationDetailFragmentDirections
                    .actionLocationDetailFragmentToLocationVisitHistoryFragment(locationId = locationId!!)
                findNavController().navigate(action)
            }
            buttonShowLocation.setOnClickListener {
                val action = LocationDetailFragmentDirections
                    .actionLocationDetailFragmentToLocationDetailMapsFragment(locationId = locationId!!)
                findNavController().navigate(action)
            }
            imageViewBackNavigation.setOnClickListener {
                requireActivity().finish()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        initUi()
    }

    private fun initUi() {
        locationInfo = viewModel.getLocationInfo(requireContext(), locationId!!)
        locationVisitHistory =
            viewModel.getAllVisitHistoryForSelectedLocation(requireContext(), locationId!!)
        locationImages = viewModel.getImageListForSelectedLocation(requireContext(), locationId!!)

        // todo "we will be update this line"
        locationInfo?.let {
            binding.apply {
                toolbarTitle.text = it.name
                textViewHistoryDate.text = it.date
                textViewShortDesc.text = it.shortDescription
                textViewLongDesc.text = it.longDescription

                var buttonDrawable: Drawable? = binding.imageViewPriority.background
                buttonDrawable = DrawableCompat.wrap(buttonDrawable!!)
                when (it.priority) {
                    1 -> {
                        DrawableCompat.setTint(
                            buttonDrawable,
                            ContextCompat.getColor(requireContext(), R.color.green)
                        )
                    }
                    2 -> {
                        DrawableCompat.setTint(
                            buttonDrawable,
                            ContextCompat.getColor(requireContext(), R.color.blue)
                        )
                    }
                    3 -> {
                        DrawableCompat.setTint(
                            buttonDrawable,
                            ContextCompat.getColor(requireContext(), R.color.gray_circle_image)
                        )
                    }
                }
                imageViewPriority.background = buttonDrawable
            }
        }

        if (locationImages[0] != null) {
            val sliderImageList = ArrayList<SlideModel>()
            locationImages.forEach {
                sliderImageList.add(SlideModel(it!!.uri, ScaleTypes.FIT))
            }
            binding.imageSlider.setImageList(sliderImageList)
        } else {
            binding.imageSlider.setImageList(arrayListOf(SlideModel("")))
        }

    }

    private fun initVisitHistoryRecycler() {
        // todo "update id for selected location"
        adapter.visitHistoryList =
            viewModel.getAllVisitHistoryForSelectedLocation(requireContext(), locationId!!).toList()
        binding.recyclerViewVisitHistory.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}