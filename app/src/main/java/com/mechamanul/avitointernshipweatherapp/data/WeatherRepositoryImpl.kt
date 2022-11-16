package com.mechamanul.avitointernshipweatherapp.data

import com.mechamanul.avitointernshipweatherapp.domain.WeatherRepository
import com.mechamanul.avitointernshipweatherapp.domain.model.DetailedDailyForecast
import com.mechamanul.avitointernshipweatherapp.domain.model.DetailedHourlyForecast
import com.mechamanul.avitointernshipweatherapp.domain.model.WeatherTime
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService,
) : WeatherRepository {


    override suspend fun getListOfCities() {
        TODO("Not yet implemented")
    }

    override suspend fun getHourlyForecast(cityName: String): DetailedHourlyForecast {
        val apiResponse = weatherService.getHourlyForecast(cityName)
        return with(apiResponse) {
            DetailedHourlyForecast(
                temperature = currentHourForecast.temperature,
                windSpeed = currentHourForecast.windSpeed,
                humidity = currentHourForecast.humidity,
                uv = currentHourForecast.uv,
                feelsLikeTemperature = currentHourForecast.feelsLikeTemperature,
                weatherDescription = currentHourForecast.weatherDescription.description,
                iconPath = appendHttps(currentHourForecast.weatherDescription.iconUrl),
                forecast = forecast.forecastResponse[0].listOfHourlyForecast.map {
                    WeatherTime(
                        it.temperature,
                        appendHttps(it.weatherDescription.iconUrl),
                        it.time
                    )
                }
            )
        }
    }

    private fun appendHttps(url: String) = "https:${url}"
    override suspend fun getDailyForecast(cityName: String): DetailedDailyForecast {
        val apiResponse = weatherService.getDailyForecast(cityName)
        return with(apiResponse.forecast) {
            DetailedDailyForecast(
                avgTemperature = forecastResponse[0].averageDayForecastResponse.averageTemperature,
                maxWindSpeed = forecastResponse[0].averageDayForecastResponse.maxWind,
                avgHumidity = forecastResponse[0].averageDayForecastResponse.averageHumidity,
                uv = forecastResponse[0].averageDayForecastResponse.uv,
                chanceOfRain = forecastResponse[0].averageDayForecastResponse.chanceOfRain,
                weatherDescription = forecastResponse[0].averageDayForecastResponse.weatherDescription.description,
                iconPath = appendHttps(forecastResponse[0].averageDayForecastResponse.weatherDescription.iconUrl),
                forecast = forecastResponse.slice(1 until forecastResponse.size).map {
                    WeatherTime(
                        it.averageDayForecastResponse.averageTemperature,
                        appendHttps(it.averageDayForecastResponse.weatherDescription.iconUrl),
                        it.date.atStartOfDay()
                    )
                }
            )
        }
    }


}