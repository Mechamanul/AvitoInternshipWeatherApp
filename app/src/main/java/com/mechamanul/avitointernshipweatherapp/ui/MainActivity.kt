package com.mechamanul.avitointernshipweatherapp.ui


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.NavigationUiSaveStateControl
import com.mechamanul.avitointernshipweatherapp.R
import com.mechamanul.avitointernshipweatherapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavigation(binding)

    }

    @OptIn(NavigationUiSaveStateControl::class)
    private fun setupNavigation(binding: ActivityMainBinding) = lifecycleScope.launch {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        with(binding) {
            navController.addOnDestinationChangedListener { controller, destination, arguments ->
                toolbar.isVisible = arguments?.getBoolean("ShowToolBar", true) ?: true
                bottomNav.isVisible = arguments?.getBoolean("ShowBottomNav", true) ?: true
            }
            toolbar.post {
                toolbar.inflateMenu(R.menu.toolbar_menu)
            }
            toolbar.setOnMenuItemClickListener {
                if (it.itemId == R.id.select_city) {
                    navController.navigate(R.id.action_global_select_city)
                }
                true
            }

            setupWithNavController(bottomNav, navController)
            // disable viewModel autosave to handle query
            setupWithNavController(
                toolbar,
                navController,
                AppBarConfiguration(
                    setOf(
                        R.id.week_forecast,
                        R.id.day_forecast,
                        R.id.initial_screen
                    )
                )
            )

        }
    }


}