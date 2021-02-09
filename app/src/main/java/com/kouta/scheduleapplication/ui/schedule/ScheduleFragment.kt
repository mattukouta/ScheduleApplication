package com.kouta.scheduleapplication.ui.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayoutMediator
import com.kouta.scheduleapplication.R
import com.kouta.scheduleapplication.databinding.FragmentScheduleBinding
import com.kouta.scheduleapplication.model.Theme
import com.kouta.scheduleapplication.util.autoCleared
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext

class ScheduleFragment : Fragment() {
    private val viewModel: ScheduleViewModel by activityViewModels()
    private var binding: FragmentScheduleBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_schedule,
            container,
            false
        )

        viewModel.getThemes()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.insert.setOnClickListener {
            viewModel.apply {
                updateCurrentItem(
                    binding.viewPager.currentItem
                )

                insertThemes(
                    Theme(name = "hoge")
                )
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.themes.collect { themes ->
                withContext(Main) {
                    val scheduleAdapter = ScheduleAdapter(this@ScheduleFragment, themes)
                    binding.viewPager.apply {
                        adapter = scheduleAdapter
                        currentItem = viewModel.currentItem.value
                    }

                    TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                        tab.text = themes[position].name + themes[position].themeId
                    }.attach()
                }
            }
        }
    }
}