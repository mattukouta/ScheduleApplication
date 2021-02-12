package com.kouta.scheduleapplication.ui.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kouta.scheduleapplication.data.repository.ThemeRepository
import com.kouta.scheduleapplication.model.Theme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val themeRepository: ThemeRepository
) : ViewModel() {
    private var _themes: MutableStateFlow<List<Theme>> = MutableStateFlow(listOf())
    val themes: StateFlow<List<Theme>> = _themes

    private var _currentItem: MutableStateFlow<Int> = MutableStateFlow(0)
    val currentItem: StateFlow<Int> = _currentItem

    private var _isFABSelected: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isFABSelected: StateFlow<Boolean> = _isFABSelected

    fun getThemes() {
        viewModelScope.launch(IO) {
            _themes.value = themeRepository.getThemes()
        }
    }

    fun insertThemes(theme: Theme) {
        viewModelScope.launch(IO) {
            themeRepository.insertTheme(theme)
            _themes.value = themeRepository.getThemes()
        }
    }

    fun updateCurrentItem(currentItem: Int) {
        _currentItem.value = currentItem
    }

    fun updateIsFABSelected(isSelected: Boolean) {
        _isFABSelected.value = isSelected
    }
}