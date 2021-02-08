package com.kouta.scheduleapplication.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kouta.scheduleapplication.model.Theme

@Dao
interface ThemeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTheme(theme: Theme)

    @Query("select * from theme_table")
    fun getThemes(): List<Theme>

    @Query("select * from theme_table where themeId=:themeId")
    fun getTheme(themeId: Int): Theme

    @Query("delete from theme_table")
    fun deleteThemes()

    @Query("delete from theme_table where themeId=:themeId")
    fun deleteTheme(themeId: Int)
}