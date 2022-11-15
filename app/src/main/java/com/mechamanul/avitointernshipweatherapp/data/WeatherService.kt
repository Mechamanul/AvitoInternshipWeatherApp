package com.example.weatherapp.data

import com.example.weatherapp.data.model.WeatherApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("forecast.json")
    fun getHourlyForecast(
        @Query("q") cityName: String,
        @Query("days") days: String = "1",
        @Query("aqi") aqi: String = "no",
        @Query("alerts") alerts: String = "no"
    ): WeatherApiResponse

    @GET("forecast.json")
    fun getDailyForecast(
        @Query("q") cityName: String,
        @Query("days") days: String = "7",
        @Query("aqi") aqi: String = "no",
        @Query("alerts") alerts: String = "no"
    ): WeatherApiResponse
}
