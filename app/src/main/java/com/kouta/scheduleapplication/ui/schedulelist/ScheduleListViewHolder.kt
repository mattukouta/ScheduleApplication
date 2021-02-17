package com.kouta.scheduleapplication.ui.schedulelist

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.kouta.scheduleapplication.databinding.ScheduleListItemBinding
import com.kouta.scheduleapplication.model.Schedule

class ScheduleListViewHolder(private val binding: ScheduleListItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(schedule: Schedule, viewLifecycleOwner: LifecycleOwner) {
        binding.run {
            lifecycleOwner = viewLifecycleOwner
            this.schedule = schedule

            executePendingBindings()
        }
    }
}