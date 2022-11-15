package com.mechamanul.avitointernshipweatherapp.domain

interface Repository {
    suspend fun getListOfCities()
    suspend fun getHourlyForecast(cityName: String)
    suspend fun getDailyForecast(cityName: String)
}
