package com.kouta.scheduleapplication.ui.scheduledetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kouta.scheduleapplication.R
import com.kouta.scheduleapplication.databinding.FragmentScheduleDetailBinding
import com.kouta.scheduleapplication.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScheduleDetailFragment: Fragment() {
    private val viewModel: ScheduleDetailViewModel by viewModels()
    private var binding: FragmentScheduleDetailBinding by autoCleared()
    private val safeArgs: ScheduleDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScheduleDetailBinding.inflate(
            inflater,
            container,
            false)
            .apply {
                lifecycleOwner = viewLifecycleOwner

                viewModel = this@ScheduleDetailFragment.viewModel
            }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener {
            val action = ScheduleDetailFragmentDirections.actionScheduleDetailFragmentToScheduleEditFragment(
                getString(R.string.title_edit),
                safeArgs.scheduleId
            )
            findNavController().navigate(action)
        }
    }
}