package com.mechamanul.avitointernshipweatherapp.ui


import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.mechamanul.avitointernshipweatherapp.R
import com.mechamanul.avitointernshipweatherapp.databinding.ActivityMainBinding
import com.mechamanul.avitointernshipweatherapp.ui.MainActivityState.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    private val requestLocationPermissionLauncher =
        registerForActivityResult(RequestMultiplePermissions(), ::onGotFineAndCoarseLocationResult)

    private fun onGotFineAndCoarseLocationResult(grantResults: Map<String, Boolean>) =
        lifecycleScope.launch {
            if (grantResults.entries.all { it.value }) {
                viewModel.requestCurrentLocation()
            } else {
                if (!shouldShowRequestPermissionRationale(Manifest.permission_group.LOCATION)) {
                    askUserForOpeningAppSettings()
                }
                findViewById<TextView>(R.id.location_instructions).text =
                    "Sorry but you have to provide permissions"
            }
        }

    private fun askUserForOpeningAppSettings() {
        val appSettingsIntent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", packageName, null)
        )
        if (packageManager.resolveActivity(
                appSettingsIntent,
                PackageManager.MATCH_DEFAULT_ONLY
            ) == null
        ) {
            Toast.makeText(this, R.string.permissions_denied_forever, Toast.LENGTH_SHORT).show()
        } else {
            AlertDialog.Builder(this)
                .setTitle(R.string.permission_denied)
                .setMessage(R.string.permission_denied_forever_message)
                .setPositiveButton(R.string.open) { _, _ ->
                    startActivity(appSettingsIntent)
                }
                .create()
                .show()
        }
    }

    private fun renderLoadingState(binding: ActivityMainBinding) {
        binding.apply {
            locationInstructions.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }
    }

    private fun renderWeatherForecast(binding: ActivityMainBinding){
        binding.apply {
            progressBar.visibility = View.GONE
            navHostFragment.visibility = View.VISIBLE
            toolbar.visibility = View.VISIBLE
            bottomNav.visibility = View.VISIBLE
        }
    }

    private fun setupNavigation(binding: ActivityMainBinding) = lifecycleScope.launch {

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
        renderWeatherForecast(binding)
    }

    private fun renderInitialState(binding: ActivityMainBinding) {
        binding.apply {
            progressBar.visibility = View.GONE
            navHostFragment.visibility = View.GONE
            toolbar.visibility = View.GONE
            bottomNav.visibility = View.GONE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        InitialState -> renderInitialState(binding)
                        Loading -> renderLoadingState(binding)
                        LocationProvided -> setupNavigation(binding)
                    }
                }
            }
        }
        requestLocationPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )

    }


}