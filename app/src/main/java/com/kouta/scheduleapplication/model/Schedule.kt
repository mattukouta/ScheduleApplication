package com.kouta.scheduleapplication.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "schedule_table")
data class Schedule(
    @PrimaryKey(autoGenerate = true)
    val scheduleId: Int = 0,
    val themeId: Int = 0,
    val title: String = "",
    @Embedded
    val deadline: TimeStamp? = null,
    val detail: String = "",
    val priority: Int = 1
) {
    data class TimeStamp(
        @Embedded
        val date: Date? = null,
        @Embedded
        val time: Time? = null
    ) {
        data class Date(
            val year: Int = 0,
            val month: Int = 0,
            val day: Int = 0,
            val dayOfWeek: String = ""
        )

        data class Time(
            val hour: Int = 0,
            val minute: Int = 0
        )
    }
}
