package com.kouta.scheduleapplication.data.database

import androidx.room.*
import com.kouta.scheduleapplication.model.Schedule

@Dao
interface ScheduleDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertSchedule(schedule: Schedule)

    @Update
    fun updateSchedule(schedule: Schedule)

    @Query("select * from schedule_table")
    fun getSchedules(): List<Schedule>

    @Query("select * from schedule_table where scheduleId=:scheduleId")
    fun getSchedule(scheduleId: Int): Schedule

    @Query("select * from schedule_table where themeId=:themeId")
    fun getThemeSchedules(themeId: Int): List<Schedule>

    @Query("delete from schedule_table")
    fun deleteSchedules()

    @Query("delete from schedule_table where scheduleId=:scheduleId")
    fun deleteSchedule(scheduleId: Int)
}