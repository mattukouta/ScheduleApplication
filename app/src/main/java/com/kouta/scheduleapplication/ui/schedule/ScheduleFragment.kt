package com.kouta.scheduleapplication.ui.schedule

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.kouta.scheduleapplication.R
import com.kouta.scheduleapplication.databinding.FragmentScheduleBinding
import com.kouta.scheduleapplication.model.Theme
import com.kouta.scheduleapplication.ui.schedule.FloatingAction.allFloatingActionButtonHide
import com.kouta.scheduleapplication.ui.schedule.FloatingAction.floatingActionButtonAnimation
import com.kouta.scheduleapplication.ui.themeaddition.ThemeAdditionDialogFragment
import com.kouta.scheduleapplication.ui.themeaddition.ThemeAdditionDialogListener
import com.kouta.scheduleapplication.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class ScheduleFragment : Fragment() {
    private val viewModel: ScheduleViewModel by viewModels()
    private var binding: FragmentScheduleBinding by autoCleared()

    lateinit var floatingActionButtonViews: List<View>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentScheduleBinding.inflate(
            inflater,
            container,
            false
        ).apply {
            lifecycleOwner = this@ScheduleFragment
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        floatingActionButtonViews = listOf(
            binding.floatingActionButtonSchedule,
            binding.floatingActionButtonTheme
        )

        setClickEvents()

        setCollects()

        viewModel.getThemes()
    }

    override fun onStop() {
        super.onStop()

        Log.d("onStop", "stop")

        if (viewModel.isFABShow.value) {
            viewModel.setIsFABShow(false)
            allFloatingActionButtonHide(
                floatingActionButtonViews
            )
        }

        updateCurrentItem()
    }

    private fun setCollects() {
        lifecycleScope.launchWhenStarted {
            viewModel.themes.collect { themes ->
                withContext(Main) {
                    val scheduleAdapter = ScheduleAdapter(this@ScheduleFragment, themes)
                    binding.viewPager.apply {
                        adapter = scheduleAdapter
                        currentItem = viewModel.currentItem.value
                    }

                    TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                        tab.text = themes[position].title
                    }.attach()
                }
            }
        }
    }

    private fun setClickEvents() {
        viewModel.let { scheduleViewModel ->
            binding.apply {
                floatingActionButtonSchedule.setOnClickListener {
                    findNavController().navigate(R.id.action_scheduleFragment_to_scheduleEditFragment)
                    updateFloatingActionButton(scheduleViewModel.isFABShow.value)
                }

                floatingActionButtonTheme.setOnClickListener {
                    showThemeAdditionDialog()
                    updateFloatingActionButton(scheduleViewModel.isFABShow.value)
                }

                floatingActionButton.setOnClickListener {
                    updateFloatingActionButton(scheduleViewModel.isFABShow.value)
                }
            }
        }
    }

    private fun updateFloatingActionButton(isShow: Boolean) {
        floatingActionButtonAnimation(
            binding.floatingActionButton,
            isShow,
            floatingActionButtonViews
        )

        viewModel.setIsFABShow(!isShow)
    }

    private fun showThemeAdditionDialog() {
        ThemeAdditionDialogFragment(
            object: ThemeAdditionDialogListener{
                override fun onPositiveButtonClick(theme: String) {
                    updateCurrentItem()

                    viewModel.insertThemes(Theme(title = theme))
                }
            }
        ).show(parentFragmentManager, null)
    }

    private fun updateCurrentItem() = viewModel.setCurrentItem(binding.viewPager.currentItem)
}