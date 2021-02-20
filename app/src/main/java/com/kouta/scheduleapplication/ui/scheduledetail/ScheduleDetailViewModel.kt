package com.kouta.scheduleapplication.ui.scheduledetail

import android.icu.util.Calendar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kouta.scheduleapplication.data.repository.ScheduleRepository
import com.kouta.scheduleapplication.model.Schedule
import com.kouta.scheduleapplication.model.Schedule.TimeStamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleDetailViewModel @Inject constructor(
    private val scheduleRepository: ScheduleRepository
): ViewModel() {

    private var _schedule: MutableLiveData<Schedule> = MutableLiveData()
    val schedule: LiveData<Schedule> = _schedule

    private var _deadline: MutableLiveData<TimeStamp> = MutableLiveData()
    val deadline: LiveData<TimeStamp> = _deadline

    private val currentTime: TimeStamp by lazy {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = java.util.Date().time

        return@lazy TimeStamp(
            date = TimeStamp.Date(
                year = calendar.get(Calendar.YEAR),
                month = calendar.get(Calendar.MONTH) + 1,
                day = calendar.get(Calendar.DATE),
                dayOfWeek = ""
            ),
            time = TimeStamp.Time(
                hour = calendar.get(Calendar.HOUR_OF_DAY),
                minute = calendar.get(Calendar.MINUTE)
            )
        )
    }

    fun getSchedule(scheduleId: Int) =
        viewModelScope.launch(IO) {
            _schedule.postValue(scheduleRepository.getSchedule(scheduleId))
        }

    private val DAY = 1000 * 60 * 60 * 24
    private val HOUR = 1000 * 60 * 60
    private val MINUTE = 1000 * 60
    fun setDeadline(timestamp: TimeStamp, currentTime: TimeStamp = this.currentTime) {
        val a = convertTimeMillis(timestamp)
        val b = convertTimeMillis(currentTime)

        val diffTime = a - b

        if (diffTime < 0) {
            _deadline.value = null
        } else {
            val diffDays = (diffTime / DAY).toInt()
            val diffHours = ((diffTime / HOUR) % 24).toInt()
            val diffMinutes = ((diffTime / MINUTE) % 60).toInt()

            _deadline.value = TimeStamp(
                date = if (diffDays > 0) TimeStamp.Date(day = diffDays) else null,
                time = TimeStamp.Time(hour = diffHours, minute = diffMinutes)
            )
        }
    }

    private fun convertTimeMillis(timestamp: TimeStamp): Long {
        val calendar = Calendar.getInstance()
        timestamp.apply {
            if (date != null && time != null) {
                calendar.set(date.year, date.month, date.day, time.hour, time.minute)
                return calendar.timeInMillis
            }
        }

        return 0
    }
}