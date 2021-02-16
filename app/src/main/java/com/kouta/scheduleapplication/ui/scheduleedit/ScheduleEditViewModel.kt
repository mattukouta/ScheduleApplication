package com.kouta.scheduleapplication.ui.scheduleedit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kouta.scheduleapplication.data.repository.ScheduleRepository
import com.kouta.scheduleapplication.data.repository.ThemeRepository
import com.kouta.scheduleapplication.model.Schedule
import com.kouta.scheduleapplication.model.Schedule.TimeStamp.Date
import com.kouta.scheduleapplication.model.Schedule.TimeStamp.Time
import com.kouta.scheduleapplication.model.Theme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleEditViewModel @Inject constructor(
    private val scheduleRepository: ScheduleRepository,
    private val themeRepository: ThemeRepository
) : ViewModel() {

    private var _themes: MutableStateFlow<List<Theme>> = MutableStateFlow(listOf())
    val themes: StateFlow<List<Theme>> = _themes

    private var _themeTitles: MutableStateFlow<List<String>> = MutableStateFlow(listOf())
    val themeTitles: StateFlow<List<String>> = _themeTitles

    private var _schedules: MutableStateFlow<List<Theme>> = MutableStateFlow(listOf())
    val schedules: StateFlow<List<Theme>> = _schedules

    private var _scheduleTitle: MutableStateFlow<String> = MutableStateFlow("")
    val scheduleTitle: StateFlow<String> = _scheduleTitle

    private var _themeSpinnerSelected: MutableStateFlow<Int> = MutableStateFlow(0)
    val themeSpinnerSelected: StateFlow<Int> = _themeSpinnerSelected

    private var _date: MutableStateFlow<Date?> = MutableStateFlow(null)
    val date: StateFlow<Date?> = _date

    private var _time: MutableStateFlow<Time?> = MutableStateFlow(null)
    val time: StateFlow<Time?> = _time

    private var _isButtonEnabled: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isButtonEnabled: StateFlow<Boolean> = _isButtonEnabled

    fun getThemes() {
        viewModelScope.launch(IO) {
            _themes.value = themeRepository.getThemes()
        }
    }

    fun createThemeTitles() {
        val titles = arrayListOf("----未選択----")
        viewModelScope.launch(IO) {
            _themes.value.forEach { theme ->
                titles.add(theme.title)
            }

            _themeTitles.value = titles.toList()
        }
    }

    fun getSchedules() {
        viewModelScope.launch(IO) {
            scheduleRepository.getSchedules()
        }
    }

    fun insertSchedule(schedule: Schedule) {
        viewModelScope.launch(IO) {
            scheduleRepository.insertSchedule(schedule)
        }
    }

    fun deleteSchedule(scheduleId: Int) {
        viewModelScope.launch(IO) {
            scheduleRepository.deleteSchedule(scheduleId)
        }
    }

    fun setScheduleTitle(text: String) {
        _scheduleTitle.value = text
    }

    fun setThemeSpinnerSelected(selectedPosition: Int) {
        _themeSpinnerSelected.value = selectedPosition
    }

    fun setDate(
        year: Int,
        month: Int,
        day: Int,
        dayOfWeek: String
    ) {
        _date.value = Date(
            year = year,
            month = month,
            day = day,
            dayOfWeek = dayOfWeek
        )
    }

    fun setTime(
        hour: Int,
        minute: Int
    ) {
        _time.value = Time(
            hour = hour,
            minute = minute
        )
    }

    fun checkButtonFlag() {
        _isButtonEnabled.value = (scheduleTitle.value.isNotBlank()
                && _themeSpinnerSelected.value != 0)
    }
}