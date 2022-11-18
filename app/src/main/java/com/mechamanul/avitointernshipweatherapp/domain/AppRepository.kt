package com.mechamanul.avitointernshipweatherapp.domain

import android.location.Location
import com.mechamanul.avitointernshipweatherapp.domain.model.City
import com.mechamanul.avitointernshipweatherapp.domain.model.DetailedForecast

interface AppRepository {
    suspend fun getSuggestedCitiesList(query: String): List<City>

    /**
     * query is cityName or latitude,longitude
     * more info https://www.weatherapi.com/docs/#intro-request
     */
//    suspend fun getHourlyForecast(query: String): DayForecastDetails
//    suspend fun getDailyForecast(query: String): WeekForecastDetails
    suspend fun getDetailedForecast(query: String): DetailedForecast
    suspend fun getCurrentLocation(): Location?
//    suspend fun getSavedCityName(): String?
//    suspend fun setSavedCityName(name: String)
}
