package com.kouta.scheduleapplication.ui.scheduleedit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.kouta.scheduleapplication.R
import com.kouta.scheduleapplication.databinding.FragmentScheduleBinding
import com.kouta.scheduleapplication.util.autoCleared

class ScheduleEditFragment : Fragment() {
    private val viewModel: ScheduleEditViewModel by activityViewModels()
    private var binding: FragmentScheduleBinding by autoCleared()

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

        return binding.root
    }

}