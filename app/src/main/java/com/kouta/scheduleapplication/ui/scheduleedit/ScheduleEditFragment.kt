package com.kouta.scheduleapplication.ui.scheduleedit

import android.annotation.SuppressLint
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.kouta.scheduleapplication.R
import com.kouta.scheduleapplication.databinding.FragmentScheduleEditBinding
import com.kouta.scheduleapplication.util.autoCleared
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext
import java.util.*

class ScheduleEditFragment : Fragment() {
    private val viewModel: ScheduleEditViewModel by activityViewModels()
    private var binding: FragmentScheduleEditBinding by autoCleared()

    private val priorities = listOf("低め", "普通", "高め")
    private val dayOfWeeks = listOf("日", "月", "火", "水", "木", "金", "土")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_schedule_edit,
            container,
            false
        )

        viewModel.getThemes()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCollects()

        setPrioritySpinner()

        setTimeStamp()

        setClickEvents()
    }

    @SuppressLint("SetTextI18n")
    private fun setCollects() {
        lifecycleScope.launchWhenStarted {
            viewModel.themes.collect {
                viewModel.createThemeTitles()
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.themeTitles.collect { titles ->
                val adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    titles.toTypedArray()
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                withContext(Dispatchers.Main) {
                    binding.spinnerTheme.adapter = adapter
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.date.collect { date ->
                binding.textViewDate.text = "${date.year}年${date.month}月${date.day}日(${date.dayOfWeek})"
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.time.collect { time ->
                binding.textViewTime.text = "${time.hour}:%0,2d".format(time.minute)
            }
        }
    }

    private fun setPrioritySpinner() {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            priorities.toTypedArray()
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerPriority.adapter = adapter
        binding.spinnerPriority.setSelection(1)
    }

    @SuppressLint("SetTextI18n")
    private fun setTimeStamp() {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = Date().time

        viewModel.setDate(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH) + 1,
            calendar.get(Calendar.DATE),
            dayOfWeeks[calendar.get(Calendar.DAY_OF_WEEK) - 1]
        )

        viewModel.setTime(
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE)
        )
    }

    private fun setClickEvents() {
        binding.textViewDate.setOnClickListener {
            DatePickerDialogFragment(
                object: DatePickerDialogListener{
                    override fun selectedDate(year: Int, month: Int, day: Int, dayOfWeek: Int) {
                        viewModel.setDate(
                            year,
                            month,
                            day,
                            dayOfWeeks[dayOfWeek]
                        )
                    }
                },
                viewModel.date.value
            ).show(parentFragmentManager, null)
        }

        binding.textViewTime.setOnClickListener {
            TimePickerDialogFragment(
                object: TimePickerDialogListener{
                    override fun selectedTime(hour: Int, minute: Int) {
                        viewModel.setTime(
                            hour,
                            minute
                        )
                    }
                },
                viewModel.time.value
            ).show(parentFragmentManager, null)

        }
    }
}