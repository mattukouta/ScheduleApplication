package com.kouta.scheduleapplication.schedulelist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.kouta.scheduleapplication.R
import com.kouta.scheduleapplication.databinding.FragmentScheduleListBinding
import com.kouta.scheduleapplication.model.Category
import com.kouta.scheduleapplication.util.autoCleared
import kotlinx.coroutines.flow.collect

class ScheduleListFragment : Fragment() {

    private val viewModel: ScheduleListViewModel by activityViewModels()
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

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenStarted {
            viewModel.categories.collect {
                Log.d("checkCollect", "更新")
            }
        }
    }
}