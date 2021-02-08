package com.kouta.scheduleapplication.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kouta.scheduleapplication.model.Category

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCategory(category: Category)

    @Query("select * from category_table")
    fun getCategories(): List<Category>

    @Query("select * from category_table where categoryId=:categoryId")
    fun getCategory(categoryId: Int): Category

    @Query("delete from category_table")
    fun deleteCategories()

    @Query("delete from category_table where categoryId=:categoryId")
    fun deleteCategory(categoryId: Int)
}