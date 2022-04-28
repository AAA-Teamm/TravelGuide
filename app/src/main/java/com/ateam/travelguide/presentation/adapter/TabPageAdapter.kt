package com.ateam.travelguide.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ateam.travelguide.presentation.ui.fragment.tab_fragments.ToBeTravelled
import com.ateam.travelguide.presentation.ui.fragment.tab_fragments.TravelledFragment

class TabPageAdapter (activity : FragmentActivity, private val tabCount: Int): FragmentStateAdapter(activity){
    override fun getItemCount(): Int = tabCount

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> ToBeTravelled()
            1 -> TravelledFragment()
            else -> ToBeTravelled()
        }
    }
}