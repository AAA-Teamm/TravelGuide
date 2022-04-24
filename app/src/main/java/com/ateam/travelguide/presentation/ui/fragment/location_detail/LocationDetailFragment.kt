package com.ateam.travelguide.presentation.ui.fragment.location_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ateam.travelguide.databinding.FragmentLocationDetailBinding

class LocationDetailFragment : Fragment() {

    private var _binding: FragmentLocationDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLocationDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // todo "we will be update this line"
        binding.toolbarTitle.text = "Konum Adi"
        binding.textViewHistoryDate.text = "12.10.2022"
        binding.textViewShortDesc.text = "Yer Kisa tanim bilgisi"
        binding.textViewLongDesc.text = "Kisa Aciklama en fazla 3 satir"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}