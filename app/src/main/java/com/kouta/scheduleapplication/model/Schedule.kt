package com.kouta.scheduleapplication.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "schedule_table")
data class Schedule(
    @PrimaryKey(autoGenerate = true)
    val scheduleId: Int = 0,
    val themeId: Int,
    val title: String,
    @Embedded
    val deadline: TimeStamp,
    val detail: String,
    val priority: Int = 2
) {
    data class TimeStamp(
        val year: Int,
        val month: Int,
        val day: Int,
        val hour: Int?,
        val minute: Int?
    )
}
