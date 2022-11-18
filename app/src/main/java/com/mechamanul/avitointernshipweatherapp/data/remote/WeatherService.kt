package com.mechamanul.avitointernshipweatherapp.data.remote

import com.mechamanul.avitointernshipweatherapp.data.remote.model.City
import com.mechamanul.avitointernshipweatherapp.data.remote.model.WeatherApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("forecast.json")
    suspend fun getHourlyForecast(
        @Query("q") cityName: String,
        @Query("days") days: String = "1",
        @Query("aqi") aqi: String = "no",
        @Query("alerts") alerts: String = "no"
    ): WeatherApiResponse


    @GET("forecast.json")
    suspend fun getDailyForecast(
        @Query("q") cityName: String,
        @Query("days") days: String = "7",
        @Query("aqi") aqi: String = "no",
        @Query("alerts") alerts: String = "no"
    ): WeatherApiResponse

    @GET("search.json")
    suspend fun getSuggestedCitiesList(@Query("q") query: String): List<City>
}
