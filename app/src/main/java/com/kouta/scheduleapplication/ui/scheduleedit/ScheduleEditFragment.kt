package com.kouta.scheduleapplication.ui.scheduleedit

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kouta.scheduleapplication.R
import com.kouta.scheduleapplication.databinding.FragmentScheduleEditBinding
import com.kouta.scheduleapplication.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext
import java.util.*

@AndroidEntryPoint
class ScheduleEditFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private val scheduleEditViewModel: ScheduleEditViewModel by viewModels()
    private var binding: FragmentScheduleEditBinding by autoCleared()
    private val safeArgs: ScheduleEditFragmentArgs by navArgs()

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
            lifecycleOwner = viewLifecycleOwner
            viewModel = scheduleEditViewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCollects()

        setObserves()

        setPrioritySpinner()

        setClickEvents()

        initSchedule()
    }

    override fun onItemSelected(
        parent: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long
    ) {
        when(parent?.id) {
            R.id.spinner_theme -> {
                scheduleEditViewModel.setThemeId(position).run {
                    scheduleEditViewModel.checkButtonFlag()
                }
            }
            R.id.spinner_priority -> scheduleEditViewModel.setPriority(position)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // すでに選択済の項目を再度選択した際の処理
    }

    @SuppressLint("SetTextI18n")
    private fun setCollects() {
        lifecycleScope.launchWhenStarted {
            scheduleEditViewModel.themes.collect {
                scheduleEditViewModel.createThemeTitles()
            }
        }

        lifecycleScope.launchWhenStarted {
            scheduleEditViewModel.themeTitles.collect { titles ->
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
    }

    private fun setObserves(){
        scheduleEditViewModel.schedule.observe(viewLifecycleOwner, {
            scheduleEditViewModel.checkButtonFlag()
        })
    }

    private fun setPrioritySpinner() {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            priorities.toTypedArray()
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerPriority.adapter = adapter
    }

    private fun setClickEvents() {
        binding.textViewDate.setOnClickListener {
            DatePickerDialogFragment(
                object: DatePickerDialogListener{
                    override fun selectedDate(year: Int, month: Int, day: Int, dayOfWeek: Int) {
                        scheduleEditViewModel.setDate(
                            year,
                            month,
                            day,
                            dayOfWeeks[dayOfWeek]
                        )
                    }
                },
                scheduleEditViewModel.schedule.value?.deadline?.date ?: return@setOnClickListener
            ).show(parentFragmentManager, null)
        }

        binding.textViewTime.setOnClickListener {
            TimePickerDialogFragment(
                object: TimePickerDialogListener{
                    override fun selectedTime(hour: Int, minute: Int) {
                        scheduleEditViewModel.setTime(
                            hour,
                            minute
                        )
                    }
                },
                scheduleEditViewModel.schedule.value?.deadline?.time ?: return@setOnClickListener
            ).show(parentFragmentManager, null)
        }

        binding.textInputScheduleTitle.doOnTextChanged { text, _, _, _ ->
            scheduleEditViewModel.setTitle(text.toString())
        }

        binding.textInputScheduleDetail.doAfterTextChanged {
            scheduleEditViewModel.setDetail(it.toString())
        }

        binding.buttonScheduleSave.setOnClickListener {
            scheduleEditViewModel.schedule.value?.let { schedule ->
                if(schedule.scheduleId == 0)
                    scheduleEditViewModel.insertSchedule(schedule)
                else
                    scheduleEditViewModel.updateSchedule(schedule)
            }

            findNavController().navigateUp()
        }

        binding.spinnerTheme.onItemSelectedListener = this
        binding.spinnerPriority.onItemSelectedListener = this
    }

    private fun initSchedule() {
        scheduleEditViewModel.apply {
            if (safeArgs.scheduleId == 0) {
                setCurrentTimeStamp(dayOfWeeks)
            }else {
                getSchedule(safeArgs.scheduleId)
            }
            getThemes()
        }
    }
}