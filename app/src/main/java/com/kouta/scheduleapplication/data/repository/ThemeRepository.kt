package com.kouta.scheduleapplication.data.repository

import com.kouta.scheduleapplication.data.database.ThemeDao
import com.kouta.scheduleapplication.model.Theme
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ThemeRepository @Inject constructor(
    private val themeDao: ThemeDao
) {
    suspend fun insertTheme(theme: Theme) =
        withContext(IO) {
            themeDao.insertTheme(theme)
        }

    suspend fun getThemes(): List<Theme> =
        withContext(IO) {
            themeDao.getThemes()
        }

    suspend fun getTheme(themeId: Int) =
        withContext(IO) {
            themeDao.getTheme(themeId)
        }

    suspend fun deleteThemes() =
        withContext(IO) {
            themeDao.deleteThemes()
        }

    suspend fun deleteTheme(themeId: Int) =
        withContext(IO) {
            themeDao.deleteTheme(themeId)
        }
}