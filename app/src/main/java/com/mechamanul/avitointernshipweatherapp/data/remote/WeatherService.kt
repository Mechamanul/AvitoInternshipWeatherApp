package com.mechamanul.avitointernshipweatherapp.data.remote

import com.mechamanul.avitointernshipweatherapp.data.remote.model.WeatherApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
//    @Headers("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8")
    @GET("forecast.json")
    suspend fun getHourlyForecast(
        @Query("q") cityName: String,
        @Query("days") days: String = "1",
        @Query("aqi") aqi: String = "no",
        @Query("alerts") alerts: String = "no"
    ): WeatherApiResponse

//    @Headers(
//        "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8",
//        "Accept-Encoding:gzip,deflate,br"
//    )
    @GET("forecast.json")
    suspend fun getDailyForecast(
        @Query("q") cityName: String,
        @Query("days") days: String = "7",
        @Query("aqi") aqi: String = "no",
        @Query("alerts") alerts: String = "no"
    ): WeatherApiResponse
}
