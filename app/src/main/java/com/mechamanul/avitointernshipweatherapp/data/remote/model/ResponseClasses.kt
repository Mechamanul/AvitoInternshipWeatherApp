package com.mechamanul.avitointernshipweatherapp.data.remote.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.time.LocalDateTime

data class WeatherApiResponse(
    @SerializedName("location") val city: City,
    @SerializedName("current") val currentHourForecast: CurrentForecast,
    @SerializedName("forecast") val forecast: Forecast,
)

data class CurrentForecast(
    @SerializedName("temp_c") val temperature: Float,
    @SerializedName("feelslike_c") val feelsLikeTemperature: Float,
    @SerializedName("humidity") val humidity: Float,
    @SerializedName("uv") val uv: Float,
    @SerializedName("wind_mph") val windSpeed: Float,
    @SerializedName("condition") val weatherDescription: WeatherDescription,
)

data class HourForecast(
    @SerializedName("temp_c") val temperature: Float,
    @SerializedName("feelslike_c") val feelsLikeTemperature: Float,
    @SerializedName("humidity") val humidity: Float,
    @SerializedName("uv") val uv: Float,
    @SerializedName("wind_mph") val windSpeed: Float,
    @SerializedName("condition") val weatherDescription: WeatherDescription,
    @SerializedName("time") val time: LocalDateTime
)

data class WeatherDescription(
    @SerializedName("text") val description: String,
    @SerializedName("icon") val iconUrl: String
)

data class Forecast(@SerializedName("forecastday") val forecastResponse: List<ForecastResponse>)

data class ForecastResponse(
    @SerializedName("date") val date: LocalDate,
    @SerializedName("day") val averageDayForecastResponse: AverageDayForecastResponse,
    @SerializedName("astro") val astro: AstroResponse,
    @SerializedName("hour") val listOfHourlyForecast: List<HourForecast>
)

data class AverageDayForecastResponse(
    @SerializedName("avgtemp_c") val averageTemperature: Float,
    @SerializedName("avghumidity") val averageHumidity: Float,
    @SerializedName("condition") val weatherDescription: WeatherDescription,
    @SerializedName("uv") val uv: Float,
    @SerializedName("daily_chance_of_rain") val chanceOfRain: Float,
    @SerializedName("maxwind_mph") val maxWind: Float
)

data class City(val id: Int, val name: String, val country: String)
data class AstroResponse(val sunrise: String, val sunset: String)
