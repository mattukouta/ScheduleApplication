package com.kouta.scheduleapplication.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kouta.scheduleapplication.model.Category

@Database(entities = [Category::class], version = 1)
abstract class CategoryDatabase: RoomDatabase() {
    abstract fun categoryDao(): CategoryDao

    companion object {
        @Volatile
        private var INSTANCE: CategoryDatabase? = null

        fun getDatabase(context: Context): CategoryDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
                
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CategoryDatabase::class.java,
                        "category"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}