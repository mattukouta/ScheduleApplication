package com.kouta.scheduleapplication.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.ui.setupActionBarWithNavController
import com.kouta.scheduleapplication.R
import com.kouta.scheduleapplication.databinding.ActivityMainBinding
import com.kouta.scheduleapplication.util.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }

        observes()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        setupBottomNavigationBar()
    }

    private fun observes() {
        viewModel.controller.observe(this,{
            setupActionBarWithNavController(it)
        })
    }

    /**
     * Called on first creation and when restoring state.
     */
    private fun setupBottomNavigationBar() {
        val navGraphIds = listOf(R.navigation.schedule, R.navigation.profile)

        viewModel.initController(
            binding.bottomNavigationView.setupWithNavController(
                navGraphIds = navGraphIds,
                fragmentManager = supportFragmentManager,
                containerId = R.id.container,
                intent = intent
            )
        )

        viewModel.controller.value?.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.scheduleEditFragment -> viewModel.hideBottomNavigation()
                else -> viewModel.showBottomNavigation()
            }
        }

        viewModel.updateCurrentNavController(currentNavController = viewModel.controller)
    }

    override fun onSupportNavigateUp(): Boolean {
        return viewModel.currentNavController.value?.navigateUp() ?: false
    }
}