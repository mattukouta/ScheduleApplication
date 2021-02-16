package com.kouta.scheduleapplication.ui.scheduleedit

import android.os.Bundle
import android.util.Log
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

class ScheduleEditFragment : Fragment() {
    private val viewModel: ScheduleEditViewModel by activityViewModels()
    private var binding: FragmentScheduleEditBinding by autoCleared()

    private val priorities = listOf("低め", "普通", "高め")

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

        setClickEvents()
    }

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

    private fun setClickEvents() {
        binding.apply {
            textViewDate.setOnClickListener {
                DatePickerDialogFragment(
                    object: DatePickerDialogListener{
                        override fun selectedDate(year: Int, month: Int, dayOfMonth: Int) {
                            Log.d("checkDataPicker", "$year : $month : $dayOfMonth")
                        }
                    }
                ).show(parentFragmentManager, null)
            }

            textViewTime.setOnClickListener {
                TimePickerDialogFragment(
                    object: TimePickerDialogListener{
                        override fun selectedTime(hourOfDay: Int, minute: Int) {
                            Log.d("checkTimePicker", "$hourOfDay : $minute")
                        }
                    }
                ).show(parentFragmentManager, null)

            }
        }
    }
}