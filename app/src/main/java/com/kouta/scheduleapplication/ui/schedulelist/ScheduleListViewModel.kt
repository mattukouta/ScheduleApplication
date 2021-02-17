package com.kouta.scheduleapplication.ui.schedulelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kouta.scheduleapplication.data.repository.ScheduleRepository
import com.kouta.scheduleapplication.data.repository.ThemeRepository
import com.kouta.scheduleapplication.model.Schedule
import com.kouta.scheduleapplication.model.Theme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ScheduleListViewModel @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) : ViewModel() {
    private var _schedules: MutableStateFlow<List<Schedule>> = MutableStateFlow(listOf())
    val schedules: StateFlow<List<Schedule>> = _schedules

    fun getThemeSchedules(themeId: Int) {
        viewModelScope.launch(IO) {
            _schedules.value = scheduleRepository.getThemeSchedules(themeId).reversed()
        }
    }
}