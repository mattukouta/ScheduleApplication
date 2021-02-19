package com.kouta.scheduleapplication.ui.schedulelist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kouta.scheduleapplication.R
import com.kouta.scheduleapplication.databinding.FragmentScheduleListBinding
import com.kouta.scheduleapplication.ui.schedule.ScheduleFragmentDirections
import com.kouta.scheduleapplication.ui.scheduledetail.ScheduleDetailFragmentDirections
import com.kouta.scheduleapplication.util.Params
import com.kouta.scheduleapplication.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ScheduleListFragment : Fragment() {
    private val viewModel: ScheduleListViewModel by viewModels()
    private var binding: FragmentScheduleListBinding by autoCleared()
    private lateinit var scheduleListAdapter: ScheduleListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentScheduleListBinding.inflate(
            inflater,
            container,
            false)
            .apply {
                lifecycleOwner = this@ScheduleListFragment

                recyclerViewScheduleList.run {
                    layoutManager = LinearLayoutManager(context)

                    adapter = ScheduleListAdapter(
                        object: ScheduleListItemListener{
                            override fun onClickItem(scheduleId: Int) {
                                val action = ScheduleFragmentDirections.actionScheduleFragmentToScheduleDetailFragment(scheduleId)
                                findNavController().navigate(action)
                            }
                        },
                        viewLifecycleOwner
                    ).also {
                        scheduleListAdapter = it
                    }
                }
            }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val themeId = arguments?.getInt(Params.Key.ADAPTER_POSITION)

        viewModel.getThemeSchedules(themeId ?: return)

        setCollects()
    }

    private fun setCollects() {
        lifecycleScope.launchWhenStarted {
            viewModel.schedules.collect { schedules ->
                scheduleListAdapter.submitList(schedules)
            }
        }
    }
}