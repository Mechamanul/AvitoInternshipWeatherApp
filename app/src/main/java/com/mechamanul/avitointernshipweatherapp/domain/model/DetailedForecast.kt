package com.mechamanul.avitointernshipweatherapp.domain.model

import java.time.LocalDateTime

data class DetailedForecast(
    val time: LocalDateTime,
    val city: City,
    val weekForecastDetails: WeekForecastDetails,
    val dayForecastDetails: DayForecastDetails,
    val dayForecast: List<WeatherTime>,
    val weekForecast: List<WeatherTime>,
)