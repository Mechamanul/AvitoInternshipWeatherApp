package com.mechamanul.avitointernshipweatherapp.ui.screens.initial_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mechamanul.avitointernshipweatherapp.domain.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InitialScreenViewModel @Inject constructor(
    private val repository: AppRepository
) :
    ViewModel() {
    private val _uiState: MutableStateFlow<UiState> =
        MutableStateFlow(UiState.InitialState)
    val uiState: StateFlow<UiState> = _uiState
    suspend fun requestCurrentLocation() = viewModelScope.launch {
        try {
            val location = repository.getCurrentLocation()
            if (location != null) {
                val latitude = location.latitude
                val longitude = location.longitude
                _uiState.value = (UiState.LocationProvided("$latitude,$longitude"))

            }
        } catch (e: Exception) {
            Log.d("exception","catched")
            _uiState.value = UiState.Error(e)
        }

    }

    fun userRefusesMessage() = viewModelScope.launch {
        _uiState.value = UiState.UserRefusedToGiveLocation
    }

    sealed class UiState {
        object InitialState : UiState()
        object UserRefusedToGiveLocation : UiState()
        data class LocationProvided(val query: String) : UiState()
        data class Error(val exception: Exception) : UiState()
    }
}