package com.mechamanul.avitointernshipweatherapp.domain

import com.mechamanul.avitointernshipweatherapp.domain.model.DetailedDailyForecast
import com.mechamanul.avitointernshipweatherapp.domain.model.DetailedHourlyForecast

interface WeatherRepository {
    suspend fun getListOfCities()
    /**
     * query is cityName or latitude,longitude
     * more info https://www.weatherapi.com/docs/#intro-request
     */
    suspend fun getHourlyForecast(query: String): DetailedHourlyForecast
    suspend fun getDailyForecast(query: String): DetailedDailyForecast
}
