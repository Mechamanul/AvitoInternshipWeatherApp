package com.mechamanul.avitointernshipweatherapp.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.mechamanul.avitointernshipweatherapp.R
import com.mechamanul.avitointernshipweatherapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        with(binding) {
            toolbar.post {
                toolbar.inflateMenu(R.menu.toolbar_menu)
            }
            toolbar.setOnMenuItemClickListener {
                if (it.itemId == R.id.select_city) {
                    navController.navigate(R.id.action_global_changeCityFragment)
                }
                true
            }
            setupWithNavController(bottomNav, navController)
            setupWithNavController(
                toolbar,
                navController,
                AppBarConfiguration(setOf(R.id.daily_forecast, R.id.hourly_forecast))
            )
        }
    }

}