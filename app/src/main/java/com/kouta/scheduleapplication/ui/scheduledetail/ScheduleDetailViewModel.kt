package com.kouta.scheduleapplication.ui.scheduledetail

import androidx.lifecycle.ViewModel
import com.kouta.scheduleapplication.data.repository.ScheduleRepository
import com.kouta.scheduleapplication.data.repository.ThemeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScheduleDetailViewModel @Inject constructor(
    private val scheduleRepository: ScheduleRepository
): ViewModel() {
}