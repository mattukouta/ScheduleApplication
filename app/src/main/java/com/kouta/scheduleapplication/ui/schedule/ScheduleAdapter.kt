package com.kouta.scheduleapplication.ui.schedule

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kouta.scheduleapplication.model.Theme
import com.kouta.scheduleapplication.ui.schedulelist.ScheduleListFragment
import com.kouta.scheduleapplication.util.Params

class ScheduleAdapter(
    fragment: Fragment,
    private val themes: List<Theme>
): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = themes.size

    override fun createFragment(position: Int): Fragment {
        val scheduleListFragment = ScheduleListFragment()
        scheduleListFragment.arguments = Bundle().apply {
            putInt(Params.Key.ADAPTER_POSITION, themes[position].themeId)
        }

        return scheduleListFragment
    }
}