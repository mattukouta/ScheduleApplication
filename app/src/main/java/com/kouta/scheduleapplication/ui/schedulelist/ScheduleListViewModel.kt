package com.kouta.scheduleapplication.ui.schedulelist

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
class ScheduleListViewModel @Inject constructor(
    private val themeRepository: ThemeRepository
) : ViewModel() {

    private var _themes: MutableStateFlow<List<Theme>> = MutableStateFlow(listOf())
    val themes: StateFlow<List<Theme>> = _themes

    fun insertTheme(theme: Theme) =
        viewModelScope.launch(IO) {
            themeRepository.insertTheme(theme)
            _themes.value = themeRepository.getThemes()
        }

    fun getThemes() =
        viewModelScope.launch(IO) {
            _themes.value = themeRepository.getThemes()
        }

    fun deleteTheme(themeId: Int) =
        viewModelScope.launch(IO) {
            themeRepository.deleteTheme(themeId)
            _themes.value = themeRepository.getThemes()
        }
}