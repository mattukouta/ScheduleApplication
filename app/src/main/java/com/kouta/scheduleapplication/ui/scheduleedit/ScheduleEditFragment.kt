package com.kouta.scheduleapplication.ui.scheduleedit

import android.annotation.SuppressLint
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.kouta.scheduleapplication.databinding.FragmentScheduleEditBinding
import com.kouta.scheduleapplication.model.Schedule
import com.kouta.scheduleapplication.model.Schedule.TimeStamp
import com.kouta.scheduleapplication.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext
import java.util.*

@AndroidEntryPoint
class ScheduleEditFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private val viewModel: ScheduleEditViewModel by viewModels()
    private var binding: FragmentScheduleEditBinding by autoCleared()

    private val priorities = listOf("低め", "普通", "高め")
    private val dayOfWeeks = listOf("日", "月", "火", "水", "木", "金", "土")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScheduleEditBinding.inflate(
            inflater,
            container,
            false
        ).apply {
            lifecycleOwner = this@ScheduleEditFragment
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCollects()

        setPrioritySpinner()

        setTimeStamp()

        setClickEvents()

        viewModel.getThemes()
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
                binding.textViewDate.text = "${date?.year}年${date?.month}月${date?.day}日(${date?.dayOfWeek})"
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.time.collect { time ->
                binding.textViewTime.text = "${time?.hour}:%0,2d".format(time?.minute)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.scheduleTitle.collect { _ ->
                viewModel.checkButtonFlag()
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.themeSpinnerSelected.collect { _ ->
                viewModel.checkButtonFlag()
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.isButtonEnabled.collect { enabled ->
                binding.buttonScheduleSave.isEnabled = enabled
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
                viewModel.date.value ?: return@setOnClickListener
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
                viewModel.time.value ?: return@setOnClickListener
            ).show(parentFragmentManager, null)
        }

        binding.textInputScheduleTitle.doOnTextChanged { text, _, _, _ ->
            viewModel.setScheduleTitle(text.toString())
        }

        binding.spinnerTheme.onItemSelectedListener = this

        binding.buttonScheduleSave.setOnClickListener {
            viewModel.insertSchedule(
                Schedule(
                    themeId = viewModel.themes.value[viewModel.themeSpinnerSelected.value - 1].themeId,
                    title = viewModel.scheduleTitle.value,
                    deadline = TimeStamp(
                        date = viewModel.date.value,
                        time = viewModel.time.value
                    ),
                    detail = binding.textInputScheduleDetail.text.toString(),
                    priority = binding.spinnerPriority.selectedItemPosition
                )
            )

            findNavController().navigateUp()
        }
    }

    override fun onItemSelected(
        parent: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long
    ) {
        viewModel.setThemeSpinnerSelected(position)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // すでに選択済の項目を再度選択した際の処理
    }
}