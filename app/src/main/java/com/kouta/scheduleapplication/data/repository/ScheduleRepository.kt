package com.kouta.scheduleapplication.data.repository

import com.kouta.scheduleapplication.data.database.ScheduleDao
import com.kouta.scheduleapplication.model.Schedule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ScheduleRepository @Inject constructor(
    private val scheduleDao: ScheduleDao
) {
    suspend fun insertSchedule(schedule: Schedule) =
        withContext(Dispatchers.IO) {
            scheduleDao.insertSchedule(schedule)
        }

    suspend fun getSchedules(): List<Schedule> =
        withContext(Dispatchers.IO) {
            scheduleDao.getSchedules()
        }

    suspend fun getSchedule(scheduleId: Int) =
        withContext(Dispatchers.IO) {
            scheduleDao.getSchedule(scheduleId)
        }

    suspend fun getThemeSchedules(themeId: Int) =
        withContext(Dispatchers.IO) {
            scheduleDao.getThemeSchedules(themeId)
        }

    suspend fun deleteSchedules() =
        withContext(Dispatchers.IO) {
            scheduleDao.deleteSchedules()
        }

    suspend fun deleteSchedule(scheduleId: Int) =
        withContext(Dispatchers.IO) {
            scheduleDao.deleteSchedule(scheduleId)
        }
}