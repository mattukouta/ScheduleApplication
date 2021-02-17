package com.kouta.scheduleapplication.ui.schedulelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.ListAdapter
import com.kouta.scheduleapplication.databinding.ScheduleListItemBinding
import com.kouta.scheduleapplication.model.Schedule

class ScheduleListAdapter(
    private val listener: ScheduleListItemListener,
    private val viewLifecycleOwner: LifecycleOwner
): ListAdapter<Schedule, ScheduleListViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ScheduleListItemBinding.inflate(inflater, parent, false)

        return ScheduleListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScheduleListViewHolder, position: Int) {
        holder.bind(getItem(position), listener, viewLifecycleOwner)
    }
}