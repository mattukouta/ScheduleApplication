package com.kouta.scheduleapplication.ui.scheduleedit

import android.annotation.SuppressLint
import android.icu.util.Calendar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    private var _schedule: MutableLiveData<Schedule> = MutableLiveData(Schedule(priority = 1))
    val schedule: LiveData<Schedule> = _schedule

    private var _selectThemePosition: MutableLiveData<Int> = MutableLiveData(0)
    val selectThemePosition: LiveData<Int> = _selectThemePosition

    private var _isButtonEnabled: MutableLiveData<Boolean> = MutableLiveData(false)
    val isButtonEnabled: LiveData<Boolean> = _isButtonEnabled

    fun getThemes() {
        viewModelScope.launch(IO) {
            _themes.value = themeRepository.getThemes()
        }
    }

    fun getSchedule(scheduleId: Int) {
        viewModelScope.launch(IO) {
            setSchedule(scheduleRepository.getSchedule(scheduleId))
            setSelectThemePosition()
        }
    }

    fun insertSchedule(schedule: Schedule) {
        viewModelScope.launch(IO) {
            scheduleRepository.insertSchedule(schedule)
        }
    }

    fun updateSchedule(schedule: Schedule) {
        viewModelScope.launch(IO) {
            scheduleRepository.updateSchedule(schedule)
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

    private suspend fun setSchedule(schedule: Schedule) {
        withContext(Main) {
            _schedule.postValue(schedule)
        }
    }

    fun setTitle(text: String) {
        _schedule.value = _schedule.value?.copy(title = text)
    }

    private suspend fun setSelectThemePosition() {
        withContext(Main) {
            val value = themes.value.indexOfFirst {
                it.themeId == _schedule.value?.themeId
            }

            _selectThemePosition.postValue(value + 1)
        }

    }

    fun setThemeId(selectedPosition: Int) {
        if (selectedPosition == 0) {
            _schedule.value = _schedule.value?.copy(
                themeId = 0
            )
        } else {
            _schedule.value = _schedule.value?.copy(
                themeId = themes.value[selectedPosition - 1].themeId
            )
        }
    }

    fun setDetail(text: String) {
        _schedule.value = schedule.value?.copy(
            detail = text
        )
    }

    fun setPriority(position: Int) {
        _schedule.value = schedule.value?.copy(
            priority = position
        )
    }

    private fun setDeadLine(
        year: Int,
        month: Int,
        day: Int,
        dayOfWeek: String,
        hour: Int,
        minute: Int
    ) {
        _schedule.postValue(
            schedule.value!!.copy(
                deadline = Schedule.TimeStamp(
                    date = Date(
                        year = year,
                        month = month,
                        day = day,
                        dayOfWeek = dayOfWeek
                    ),
                    time = Time(
                        hour = hour,
                        minute = minute
                    )
                )
            )
        )
    }

    fun setDate(
        year: Int,
        month: Int,
        day: Int,
        dayOfWeek: String
    ) {
        _schedule.postValue(
            schedule.value?.let {
                it.copy(
                    deadline = Schedule.TimeStamp(
                        date = Date(
                            year = year,
                            month = month,
                            day = day,
                            dayOfWeek = dayOfWeek
                        ),
                        time = it.deadline?.time
                    )
                )
            }
        )
    }

    fun setTime(
        hour: Int,
        minute: Int
    ) {
        _schedule.postValue(
            schedule.value?.let {
                it.copy(
                    deadline = Schedule.TimeStamp(
                        date = it.deadline?.date,
                        time = Time(
                            hour = hour,
                            minute = minute
                        )
                    )
                )
            }
        )
    }

    @SuppressLint("SetTextI18n")
    fun setCurrentTimeStamp(dayOfWeeks: List<String>) {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = java.util.Date().time

        setDeadLine(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH) + 1,
            calendar.get(Calendar.DATE),
            dayOfWeeks[calendar.get(Calendar.DAY_OF_WEEK) - 1],
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE)
        )
    }

    fun checkButtonFlag() {
        _isButtonEnabled.value = (!_schedule.value?.title.isNullOrBlank()
                && _schedule.value?.themeId != 0)
    }
}