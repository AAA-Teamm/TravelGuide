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
import com.ateam.travelguide.R
import com.ateam.travelguide.databinding.FragmentLocationDetailBinding
import com.ateam.travelguide.model.Location
import com.ateam.travelguide.presentation.adapter.VisitHistoryAdapter

class LocationDetailFragment : Fragment() {

    private var _binding: FragmentLocationDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: VisitHistoryAdapter
    private lateinit var viewModel: LocationDetailViewModel
    private var locationInfo: Location? = null

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
    }

    private fun initClickListeners() {
        binding.apply {
            buttonAddVisit.setOnClickListener {
                // no-op
            }
            buttonShowLocation.setOnClickListener {
                // no-op
            }
        }
    }

    private fun initUi() {
        // todo "update id for selected location"
        locationInfo = viewModel.getLocationInfo(requireContext(), 0)

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
    }

    private fun initVisitHistoryRecycler() {
        // todo "update id for selected location"
        adapter.visitHistoryList =
            viewModel.getAllVisitHistoryForSelectedLocation(requireContext(), 0)
        binding.recyclerViewVisitHistory.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}