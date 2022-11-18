package com.mechamanul.avitointernshipweatherapp.domain.model

data class WeekForecastDetails(
    val avgTemperature: Float,
    val maxWindSpeed: Float,
    val avgHumidity: Float,
    val uv: Float,
    val chanceOfRain: Float,
    val weatherDescription: String,
    val iconPath: String
)