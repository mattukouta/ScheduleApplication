package com.kouta.scheduleapplication.schedulelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kouta.scheduleapplication.data.repository.CategoryRepository
import com.kouta.scheduleapplication.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ScheduleListViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private var _categories: MutableStateFlow<List<Category>> = MutableStateFlow(listOf())
    val categories: StateFlow<List<Category>> = _categories

    fun insertCategory(category: Category) =
        viewModelScope.launch(IO) {
            categoryRepository.insertCategory(category)
            _categories.value = categoryRepository.getCategories()
        }

    fun getCategories() =
        viewModelScope.launch(IO) {
            _categories.value = categoryRepository.getCategories()
        }

    fun deleteCategory(categoryId: Int) =
        viewModelScope.launch(IO) {
            categoryRepository.deleteCategory(categoryId)
            _categories.value = categoryRepository.getCategories()
        }
}