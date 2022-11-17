package com.mechamanul.avitointernshipweatherapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mechamanul.avitointernshipweatherapp.domain.common.ApiResult
import com.mechamanul.avitointernshipweatherapp.domain.model.DetailedDailyForecast
import com.mechamanul.avitointernshipweatherapp.domain.model.DetailedHourlyForecast
import com.mechamanul.avitointernshipweatherapp.domain.usecases.GetCurrentUserLocationUseCase
import com.mechamanul.avitointernshipweatherapp.domain.usecases.GetDailyForecastUseCase
import com.mechamanul.avitointernshipweatherapp.domain.usecases.GetHourlyForecastUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias DetailedDailyForecastResult = ApiResult<DetailedDailyForecast>
typealias DetailedHourlyForecastApiResult = ApiResult<DetailedHourlyForecast>


@HiltViewModel
class MainViewModel @Inject constructor(
    private val getDailyForecastUseCase: GetDailyForecastUseCase,
    private val getHourlyForecastUseCase: GetHourlyForecastUseCase,
    private val getCurrentUserLocationUseCase: GetCurrentUserLocationUseCase
) :
    ViewModel() {
    suspend fun requestCurrentLocation() = viewModelScope.launch {
        _uiState.value = MainActivityState.Loading
        val location = getCurrentUserLocationUseCase.execute()
        Log.d("viewModel", "$location")
        if (location != null) {
            val latitude = location.latitude
            val longitude = location.longitude
            getForecasts(query = "${latitude},${longitude}")
        }
    }

    private suspend fun getForecasts(query: String) = viewModelScope.launch {
        _hourlyForecast.emit(getHourlyForecastUseCase.execute(query))
        _dailyForecast.emit(getDailyForecastUseCase.execute(query))
        _uiState.value = MainActivityState.LocationProvided
    }

    private val _uiState: MutableStateFlow<MainActivityState> =
        MutableStateFlow(MainActivityState.InitialState)
    val uiState: StateFlow<MainActivityState> = _uiState


    private val _dailyForecast: MutableSharedFlow<DetailedDailyForecastResult> =
        MutableSharedFlow(replay = 1)
    val dailyForecast: SharedFlow<DetailedDailyForecastResult> = _dailyForecast
    private val _hourlyForecast: MutableSharedFlow<DetailedHourlyForecastApiResult> =
        MutableSharedFlow(replay = 1)
    val hourlyForecast: SharedFlow<DetailedHourlyForecastApiResult> = _hourlyForecast


}