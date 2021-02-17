package com.kouta.scheduleapplication.ui.schedulelist

import androidx.recyclerview.widget.DiffUtil
import com.kouta.scheduleapplication.model.Schedule

internal object DiffCallback: DiffUtil.ItemCallback<Schedule>() {
    override fun areItemsTheSame(oldItem: Schedule, newItem: Schedule): Boolean =
        oldItem.scheduleId == newItem.scheduleId

    override fun areContentsTheSame(oldItem: Schedule, newItem: Schedule): Boolean =
        oldItem == newItem
}