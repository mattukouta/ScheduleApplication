package com.kouta.scheduleapplication.ui.schedule

import android.os.Bundle
import android.util.Log
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
                        tab.text = themes[position].name + themes[position].themeId
                    }.attach()
                }
            }
        }
    }

    private fun setClickEvents() {
        viewModel.let { scheduleViewModel ->
            binding.apply {
                floatingActionButtonSchedule.setOnClickListener {
                    updateFloatingActionButton(scheduleViewModel.isFABShow.value)
                }

                floatingActionButtonTheme.setOnClickListener {
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

    private fun showIn(view: View) {
        view.apply {
            visibility = View.VISIBLE
            alpha = 0f
            translationY = 120f
            animate()
                .setDuration(200)
                .translationY(0f)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                    }
                })
                .alpha(1f)
                .start()
        }
    }

    private fun allShowIn() {
        showIn(binding.floatingActionButtonSchedule)
        showIn(binding.floatingActionButtonTheme)
    }

    private fun showOut(view: View) {
        view.apply {
            visibility = View.VISIBLE
            alpha = 1f
            translationY = 0f
            animate()
                .setDuration(200)
                .translationY(view.height.toFloat())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        view.visibility = View.GONE
                        super.onAnimationEnd(animation)
                    }
                }).alpha(0f)
                .start()
        }
    }
}