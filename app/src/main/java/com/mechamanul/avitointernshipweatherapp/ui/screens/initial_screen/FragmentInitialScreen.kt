package com.mechamanul.avitointernshipweatherapp.ui.screens.initial_screen;

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.mechamanul.avitointernshipweatherapp.R
import com.mechamanul.avitointernshipweatherapp.databinding.FragmentInitialScreenBinding
import com.mechamanul.avitointernshipweatherapp.domain.common.ApiResult
import com.mechamanul.avitointernshipweatherapp.ui.MainViewModel
import com.mechamanul.avitointernshipweatherapp.ui.screens.initial_screen.InitialScreenViewModel.UiState.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentInitialScreen : Fragment() {
    private val initialScreenViewModel: InitialScreenViewModel by viewModels()
    val viewModel: MainViewModel by activityViewModels()
    private val requestLocationPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions(),
            ::onGotFineAndCoarseLocationResult
        )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_initial_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentInitialScreenBinding.bind(view)
        binding.apply {
            browseCityManuallyButton.setOnClickListener {
                findNavController().navigate(R.id.action_global_select_city)
            }
            permissionDialogButton.setOnClickListener {
                requestPermissions()
            }
            progressBar.visibility = View.GONE
            requestPermissions()
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    initialScreenViewModel.uiState.collect { state ->
                        when (state) {
                            is Loading -> binding.apply {
                                locationInstructions.visibility = View.GONE
                                browseCityManuallyButton.visibility = View.GONE
                                permissionDialogButton.visibility = View.GONE
                                progressBar.visibility = View.VISIBLE
                                Log.d("Loading", "started")

                            }
                            is LocationProvided -> {
                                viewModel.setLocation(state.query)
                            }
                            is UserRefusedToGiveLocation -> Snackbar.make(
                                binding.root,
                                "You are able to relaunch permission dialog or choose city manually",
                                Snackbar.LENGTH_LONG
                            ).show()
                            is Error -> {
                                binding.apply {
                                    locationInstructions.visibility = View.VISIBLE
                                    browseCityManuallyButton.visibility = View.VISIBLE
                                    permissionDialogButton.visibility = View.VISIBLE
                                    progressBar.visibility = View.GONE
                                }
                                Snackbar.make(
                                    binding.root,
                                    "${state.exception.message}",
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
                launch {
                    viewModel.detailedForecast.collect {
                        Log.d("ApiResult", "$it")
                        if (it is ApiResult.Success) {
                            findNavController().popBackStack()
                        }
                    }
                }
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun requestPermissions() {
        requestLocationPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun onGotFineAndCoarseLocationResult(grantResults: Map<String, Boolean>) =
        if (grantResults.entries.any { it.value }) {
            Log.d("anyGrant", "triggered")
            initialScreenViewModel.requestCurrentLocation()
        } else {
            initialScreenViewModel.userRefusesMessage()
        }
}
