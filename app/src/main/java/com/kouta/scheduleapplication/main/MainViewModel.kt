package com.kouta.scheduleapplication.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

class MainViewModel: ViewModel() {
    private var _currentNavController: MutableLiveData<NavController> = MutableLiveData()
    val currentNavController: LiveData<NavController> get() = _currentNavController

//    private var _controller: MutableLiveData<NavController> = MutableLiveData()
    lateinit var controller: LiveData<NavController>

    fun updateCurrentNavController(currentNavController: LiveData<NavController>) {
        _currentNavController.value = currentNavController.value
    }

    fun initController(navController: LiveData<NavController>) {
        controller = navController
    }
}