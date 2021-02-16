package com.kouta.scheduleapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "theme_table")
data class Theme(
    @PrimaryKey(autoGenerate = true)
    val themeId: Int = 0,
    val title: String
)
