package com.mechamanul.avitointernshipweatherapp.domain.model

data class DayForecastDetails(
    val temperature: Float,
    val windSpeed: Float,
    val humidity: Float,
    val uv: Float,
    val feelsLikeTemperature: Float,
    val weatherDescription: String,
    val iconPath: String
)
