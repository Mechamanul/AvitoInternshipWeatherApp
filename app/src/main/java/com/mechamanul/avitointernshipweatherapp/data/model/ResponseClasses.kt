package com.example.weatherapp.data.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class WeatherApiResponse(
    @SerializedName("current") val currentHourForecast: HourForecast,
    @SerializedName("forecast") val forecast: Forecast
)

data class HourForecast(
    @SerializedName("temp_c") val temperature: Float,
    @SerializedName("feelslike_c") val feelsLikeTemperature: Float,
    @SerializedName("humidity") val humidity: Float,
    @SerializedName("uv") val uv: Float,
    @SerializedName("wind_mph") val windSpeed: Float,
    @SerializedName("condition") val weatherDescription: WeatherDescription,
    @SerializedName("time") val time: LocalDateTime?
)

data class WeatherDescription(
    @SerializedName("text") val description: String,
    @SerializedName("icon") val iconUrl: String
)

data class Forecast(@SerializedName("forecastday") val forecastResponse: List<ForecastResponse>)

data class ForecastResponse(
    @SerializedName("date") val date: LocalDateTime,
    @SerializedName("day") val averageDayForecastResponse: AverageDayForecastResponse,
    @SerializedName("astro") val astro: AstroResponse,
    @SerializedName("hour") val listOfHourlyForecast: List<HourForecast>
)

data class AverageDayForecastResponse(
    @SerializedName("avg_temp") val averageTemperature: Float,
    @SerializedName("avg_humidity") val averageHumidity: Float,
    @SerializedName("condition") val weatherDescription: WeatherDescription,
    @SerializedName("uv") val uv: Float,
    @SerializedName("chance_of_rain") val chanceOfRain: Float,
    @SerializedName("maxwind_mph") val maxWind: Float
)

data class AstroResponse(val sunrise: String, val sunset: String)
