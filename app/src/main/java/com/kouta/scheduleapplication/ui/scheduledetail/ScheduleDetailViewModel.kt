package com.kouta.scheduleapplication.ui.scheduledetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kouta.scheduleapplication.data.repository.ScheduleRepository
import com.kouta.scheduleapplication.model.Schedule
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

    fun getSchedule(scheduleId: Int) =
        viewModelScope.launch(IO) {
            _schedule.postValue(scheduleRepository.getSchedule(scheduleId))
        }
}