package com.kouta.scheduleapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_table")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val categoryId: Int = 0,
    val name: String
)
