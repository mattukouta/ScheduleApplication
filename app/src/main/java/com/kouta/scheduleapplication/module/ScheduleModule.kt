package com.kouta.scheduleapplication.module

import android.content.Context
import com.kouta.scheduleapplication.data.database.ThemeDao
import com.kouta.scheduleapplication.data.database.ThemeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ScheduleModule {
    @Provides
    @Singleton
    fun provideThemeDao(@ApplicationContext context: Context): ThemeDao =
        ThemeDatabase.getDatabase(context).themeDao()
}