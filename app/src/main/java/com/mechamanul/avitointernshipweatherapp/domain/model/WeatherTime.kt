package com.mechamanul.avitointernshipweatherapp.domain.model

import java.time.LocalDateTime

data class WeatherTime(
    val temperature: Float,
    val weatherIconPath: String,
    val datetime: LocalDateTime
)