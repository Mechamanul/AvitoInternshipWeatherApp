package com.mechamanul.avitointernshipweatherapp.domain

import com.mechamanul.avitointernshipweatherapp.domain.model.DetailedDailyForecast
import com.mechamanul.avitointernshipweatherapp.domain.model.DetailedHourlyForecast

interface WeatherRepository {
    suspend fun getListOfCities()
    suspend fun getHourlyForecast(cityName: String): DetailedHourlyForecast
    suspend fun getDailyForecast(cityName: String): DetailedDailyForecast
}
