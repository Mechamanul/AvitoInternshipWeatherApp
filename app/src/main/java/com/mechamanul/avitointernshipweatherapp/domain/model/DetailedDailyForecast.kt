package com.mechamanul.avitointernshipweatherapp.domain.model

data class DetailedDailyForecast(
    val avgTemperature: Float,
    val maxWindSpeed: Float,
    val avgHumidity: Float,
    val uv: Float,
    val chanceOfRain: Float,
    val forecast: List<WeatherTime>,
    val weatherDescription: String,
    val iconPath: String
)