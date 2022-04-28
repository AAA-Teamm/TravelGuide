package com.ateam.travelguide.presentation.ui.fragment.location_detail

import android.app.Dialog
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ateam.travelguide.R
import com.ateam.travelguide.databinding.FragmentLocationDetailBinding
import com.ateam.travelguide.databinding.FullScreenImagePopupBinding
import com.ateam.travelguide.model.Image
import com.ateam.travelguide.model.Location
import com.ateam.travelguide.model.VisitHistory
import com.ateam.travelguide.presentation.adapter.VisitHistoryAdapter
import com.ateam.travelguide.util.Constant.LOCATION_ID
import com.ateam.travelguide.util.getTheDay
import com.ateam.travelguide.util.getTheDayWithSeparate
import com.ateam.travelguide.util.writeDate
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemChangeListener
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel

class LocationDetailFragment : Fragment() {

    private var _binding: FragmentLocationDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: VisitHistoryAdapter
    private lateinit var viewModel: LocationDetailViewModel
    private lateinit var locationInfo: Location
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
            imageSlider.setItemClickListener(object : ItemClickListener {
                override fun onItemSelected(position: Int) {
                    showImageInFullScreenMode(locationImages[position]!!)
                }
            })
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

        binding.apply {
            toolbarTitle.text = locationInfo.name
            textViewShortDesc.text = locationInfo.shortDescription
            textViewLongDesc.text = locationInfo.longDescription

            var buttonDrawable: Drawable? = binding.imageViewPriority.background
            buttonDrawable = DrawableCompat.wrap(buttonDrawable!!)
            when (locationInfo.priority) {
                0 -> {
                    DrawableCompat.setTint(
                        buttonDrawable,
                        ContextCompat.getColor(requireContext(), R.color.green)
                    )
                }
                1 -> {
                    DrawableCompat.setTint(
                        buttonDrawable,
                        ContextCompat.getColor(requireContext(), R.color.blue)
                    )
                }
                2 -> {
                    DrawableCompat.setTint(
                        buttonDrawable,
                        ContextCompat.getColor(requireContext(), R.color.gray_circle_image)
                    )
                }
            }
            imageViewPriority.background = buttonDrawable
        }

        if (locationImages.size > 0) {
            val sliderImageList = ArrayList<SlideModel>()
            locationImages.forEach { image ->
                image?.let {
                    sliderImageList.add(SlideModel(it.uri, ScaleTypes.FIT))
                }
            }
            binding.imageSlider.setImageList(sliderImageList)
        } else {
            binding.imageSlider.visibility = View.GONE
            binding.textViewHistoryDate.visibility = View.GONE
        }

        binding.textViewHistoryDate.text = locationInfo.date

        initVisitHistoryRecycler()
    }

    private fun initVisitHistoryRecycler() {
        if (locationVisitHistory.size > 0) {
            adapter.visitHistoryList = viewModel
                .getAllVisitHistoryForSelectedLocation(requireContext(), locationId!!).toList()
            binding.recyclerViewVisitHistory.adapter = adapter
        } else {
            binding.textViewVisitHistory.visibility = View.GONE
            binding.recyclerViewVisitHistory.visibility = View.GONE
        }
    }

    private fun showImageInFullScreenMode(image: Image) {
        val dialog = Dialog(requireContext())
        val binding = FullScreenImagePopupBinding.inflate(
            LayoutInflater.from(requireContext())
        )

        dialog.setContentView(binding.root)
        dialog.setCancelable(true)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )

        binding.imageView.setImageURI(Uri.parse(image.uri))

        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}