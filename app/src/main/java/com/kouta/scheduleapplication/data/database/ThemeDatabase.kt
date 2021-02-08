package com.kouta.scheduleapplication.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kouta.scheduleapplication.model.Theme

@Database(entities = [Theme::class], version = 1)
abstract class ThemeDatabase: RoomDatabase() {
    abstract fun themeDao(): ThemeDao

    companion object {
        @Volatile
        private var INSTANCE: ThemeDatabase? = null

        fun getDatabase(context: Context): ThemeDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
                
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ThemeDatabase::class.java,
                        "theme"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}