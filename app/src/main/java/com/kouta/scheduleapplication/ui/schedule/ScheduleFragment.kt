package com.kouta.scheduleapplication.ui.schedule

import android.os.Bundle
import android.os.Parcel
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.kouta.scheduleapplication.R
import com.kouta.scheduleapplication.databinding.FragmentScheduleBinding
import com.kouta.scheduleapplication.model.Theme
import com.kouta.scheduleapplication.ui.themeaddition.ThemeAdditionDialogListener
import com.kouta.scheduleapplication.ui.schedule.FloatingAction.allFloatingActionButtonHide
import com.kouta.scheduleapplication.ui.schedule.FloatingAction.floatingActionButtonAnimation
import com.kouta.scheduleapplication.util.autoCleared
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext

class ScheduleFragment : Fragment() {
    private val viewModel: ScheduleViewModel by activityViewModels()
    private var binding: FragmentScheduleBinding by autoCleared()

    lateinit var floatingActionButtonViews: List<View>

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

        floatingActionButtonViews = listOf(
            binding.floatingActionButtonSchedule,
            binding.floatingActionButtonTheme
        )

        setClickEvents()

        setCollects()
    }

    override fun onStop() {
        super.onStop()

        Log.d("onStop", "stop")

        if (viewModel.isFABShow.value) {
            viewModel.updateIsFABShow(false)
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
                        tab.text = themes[position].name
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

        viewModel.updateIsFABShow(!isShow)
    }

    private fun showThemeAdditionDialog() {
        val action = ScheduleFragmentDirections
            .actionScheduleFragmentToThemeAdditionDialogFragment(
                object : ThemeAdditionDialogListener {
                    override fun onPositiveButtonClick(theme: String) {
                        updateCurrentItem()

                        viewModel.insertThemes(Theme(name = theme))
                    }

                    // ToDo: 処理を把握できていない
                    override fun describeContents(): Int = 0
                    override fun writeToParcel(dest: Parcel?, flags: Int) {}
                }
            )

        findNavController().navigate(action)
    }

    private fun updateCurrentItem() = viewModel.updateCurrentItem(binding.viewPager.currentItem)
}