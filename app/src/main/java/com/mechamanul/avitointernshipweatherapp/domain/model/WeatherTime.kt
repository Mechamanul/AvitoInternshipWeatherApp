package com.mechamanul.avitointernshipweatherapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class WeatherTime(
    val temperature: Float,
    val weatherIconPath: String,
    val time: LocalDateTime
) : Parcelable