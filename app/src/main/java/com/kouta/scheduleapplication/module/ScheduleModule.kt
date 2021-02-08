package com.kouta.scheduleapplication.module

import android.content.Context
import com.kouta.scheduleapplication.data.database.CategoryDao
import com.kouta.scheduleapplication.data.database.CategoryDatabase
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
    fun provideCategoryDao(@ApplicationContext context: Context): CategoryDao =
        CategoryDatabase.getDatabase(context).categoryDao()
}