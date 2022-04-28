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
            imageSlider.setItemClickListener(object : ItemClickListener {
                override fun onItemSelected(position: Int) {
                    showImageInFullScreenMode(locationImages[position]!!)
                }
            })
            imageSlider.setItemChangeListener(object : ItemChangeListener {
                override fun onItemChanged(position: Int) {
                    textViewHistoryDate.text = String().writeDate(
                        locationImages[position]!!.day!!,
                        locationImages[position]!!.month!!,
                        locationImages[position]!!.year!!
                    )
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

        locationInfo?.let {
            binding.apply {
                toolbarTitle.text = it.name
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

        if (locationImages.size > 0 && locationImages[0] != null) {
            val sliderImageList = ArrayList<SlideModel>()
            locationImages.forEach {
                if (it != null) {
                    sliderImageList.add(SlideModel(it.uri, ScaleTypes.FIT))
                }
            }
            binding.imageSlider.setImageList(sliderImageList)
        }

        initVisitHistoryRecycler()
    }

    private fun initVisitHistoryRecycler() {
        adapter.visitHistoryList =
            viewModel.getAllVisitHistoryForSelectedLocation(requireContext(), locationId!!).toList()
        binding.recyclerViewVisitHistory.adapter = adapter
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