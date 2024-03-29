package com.kouta.scheduleapplication.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel: ViewModel() {
    private var _currentNavController: MutableStateFlow<NavController?> = MutableStateFlow(null)
    val currentNavController: StateFlow<NavController?> = _currentNavController

    lateinit var controller: LiveData<NavController>

    private var _isBottomNavigationVisible: MutableLiveData<Boolean> = MutableLiveData(true)
    val isBottomNavigationVisible: LiveData<Boolean> = _isBottomNavigationVisible

    fun updateCurrentNavController(currentNavController: LiveData<NavController>) {
        _currentNavController.value = currentNavController.value
    }

    fun initController(navController: LiveData<NavController>) {
        controller = navController
    }

    fun hideBottomNavigation() {
        _isBottomNavigationVisible.value = false
    }

    fun showBottomNavigation() {
        _isBottomNavigationVisible.value = true
    }
}