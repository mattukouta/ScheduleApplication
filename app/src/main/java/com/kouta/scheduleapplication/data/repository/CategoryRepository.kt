package com.kouta.scheduleapplication.data.repository

import com.kouta.scheduleapplication.data.database.CategoryDao
import com.kouta.scheduleapplication.model.Category
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val categoryDao: CategoryDao
) {
    suspend fun insertCategory(category: Category) =
        withContext(IO) {
            categoryDao.insertCategory(category)
        }

    suspend fun getCategories(): List<Category> =
        withContext(IO) {
            categoryDao.getCategories()
        }

    suspend fun getCategory(categoryId: Int) =
        withContext(IO) {
            categoryDao.getCategory(categoryId)
        }

    suspend fun deleteCategories() =
        withContext(IO) {
            categoryDao.deleteCategories()
        }

    suspend fun deleteCategory(categoryId: Int) =
        withContext(IO) {
            categoryDao.deleteCategory(categoryId)
        }
}