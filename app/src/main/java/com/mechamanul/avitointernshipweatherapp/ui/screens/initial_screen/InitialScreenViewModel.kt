package com.mechamanul.avitointernshipweatherapp.ui.screens.initial_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mechamanul.avitointernshipweatherapp.domain.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InitialScreenViewModel @Inject constructor(
    private val repository: AppRepository
) :
    ViewModel() {
    private val _uiState: MutableSharedFlow<UiState> =
        MutableSharedFlow()
    val uiState: SharedFlow<UiState> = _uiState
    fun requestCurrentLocation() = viewModelScope.launch {
        try {
            _uiState.emit(UiState.Loading)
            val location = repository.getCurrentLocation()
            if (location != null) {
                val latitude = location.latitude
                val longitude = location.longitude
                _uiState.emit(UiState.LocationProvided("$latitude,$longitude"))
            }
        } catch (e: Exception) {
            _uiState.emit(UiState.Error(e))
        }

    }

    fun userRefusesMessage() = viewModelScope.launch {
        _uiState.emit(UiState.UserRefusedToGiveLocation)
    }

    sealed class UiState {
        object UserRefusedToGiveLocation : UiState()
        data class LocationProvided(val query: String) : UiState()
        data class Error(val exception: Exception) : UiState()
        object Loading : UiState()
    }
}