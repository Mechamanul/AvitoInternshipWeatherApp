package com.mechamanul.avitointernshipweatherapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DayForecastDetails(
    val temperature: Float,
    val windSpeed: Float,
    val humidity: Float,
    val uv: Float,
    val feelsLikeTemperature: Float,
    val weatherDescription: String,
    val iconPath: String
) : Parcelable
