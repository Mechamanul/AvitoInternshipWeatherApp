package com.mechamanul.avitointernshipweatherapp.ui

import com.mechamanul.avitointernshipweatherapp.domain.model.DetailedHourlyForecast

sealed class MainActivityState {
    object Loading : MainActivityState()
    object InitialState : MainActivityState()
    object LocationProvided : MainActivityState()
}
