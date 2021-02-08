package com.kouta.scheduleapplication.schedulelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kouta.scheduleapplication.R
import com.kouta.scheduleapplication.databinding.FragmentScheduleListBinding
import com.kouta.scheduleapplication.util.autoCleared

class ScheduleListFragment : Fragment() {

    private val viewModel: ScheduleListViewModel by viewModels()
    private var binding: FragmentScheduleListBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_schedule_list,
            container,
            false
        )

        binding.next.setOnClickListener {
            findNavController().navigate(R.id.action_scheduleListFragment_to_editFragment)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}