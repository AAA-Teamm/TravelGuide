package com.ateam.travelguide.presentation.ui.fragment.location_visit_history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ateam.travelguide.R
import com.ateam.travelguide.databinding.ActivityAddBinding.inflate
import com.ateam.travelguide.databinding.FragmentLocationDetailBinding
import com.ateam.travelguide.databinding.FragmentLocationVisitHistoryBinding

class LocationVisitHistoryFragment : Fragment() {

    private var _binding: FragmentLocationVisitHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLocationVisitHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // todo "we will be update this line"
        binding.apply {
            toolbarTitle.text = "Konum Adi"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}