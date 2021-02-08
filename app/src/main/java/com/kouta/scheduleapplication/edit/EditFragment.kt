package com.kouta.scheduleapplication.edit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.kouta.scheduleapplication.R
import com.kouta.scheduleapplication.databinding.FragmentEditBinding
import com.kouta.scheduleapplication.util.autoCleared

class EditFragment : Fragment() {
    private val viewModel: EditViewModel by activityViewModels()
    private var binding: FragmentEditBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_edit,
            container,
            false
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}