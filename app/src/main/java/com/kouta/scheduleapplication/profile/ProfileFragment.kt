package com.kouta.scheduleapplication.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.kouta.scheduleapplication.R
import com.kouta.scheduleapplication.databinding.FragmentProfileBinding
import com.kouta.scheduleapplication.util.autoCleared

class ProfileFragment : Fragment() {
    private val viewModel: ProfileViewModel by activityViewModels()
    private var binding: FragmentProfileBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_profile,
            container,
            false
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}