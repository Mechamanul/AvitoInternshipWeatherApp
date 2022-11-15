package com.mechamanul.avitointernshipweatherapp.domain.model

data class DetailedHourlyForecast(
    val temperature: Float,
    val windSpeed: Float,
    val humidity: Float,
    val uv: Float,
    val feelsLikeTemperature: Float,
    val forecast: List<WeatherTime>,
    val weatherDescription: String,
    val iconPath: String
)
