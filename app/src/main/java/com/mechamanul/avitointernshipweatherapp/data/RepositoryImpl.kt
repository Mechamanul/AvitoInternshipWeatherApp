package com.example.weatherapp.data

import com.mechamanul.avitointernshipweatherapp.domain.Repository
import com.mechamanul.avitointernshipweatherapp.domain.model.DetailedDailyForecast
import com.mechamanul.avitointernshipweatherapp.domain.model.DetailedHourlyForecast
import com.mechamanul.avitointernshipweatherapp.domain.model.WeatherTime

import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val weatherService: WeatherService,
) : Repository {


    override suspend fun getListOfCities() {
        TODO("Not yet implemented")
    }

    override suspend fun getHourlyForecast(cityName: String) {
        val apiResponse = weatherService.getHourlyForecast(cityName)
        return with(apiResponse) {
            DetailedHourlyForecast(
                temperature = currentHourForecast.temperature,
                windSpeed = currentHourForecast.windSpeed,
                humidity = currentHourForecast.humidity,
                uv = currentHourForecast.uv,
                feelsLikeTemperature = currentHourForecast.feelsLikeTemperature,
                weatherDescription = currentHourForecast.weatherDescription.description,
                iconPath = currentHourForecast.weatherDescription.iconUrl,
                forecast = forecast.forecastResponse[0].listOfHourlyForecast.map {
                    WeatherTime(
                        it.temperature,
                        it.weatherDescription.iconUrl,
                        it.time ?: throw Exception("Forecast response does not contain time")
                    )
                }
            )
        }
    }

    override suspend fun getDailyForecast(cityName: String) {
        val apiResponse = weatherService.getDailyForecast(cityName)
        return with(apiResponse.forecast) {
            DetailedDailyForecast(
                avgTemperature = forecastResponse[0].averageDayForecastResponse.averageTemperature,
                maxWindSpeed = forecastResponse[0].averageDayForecastResponse.maxWind,
                avgHumidity = forecastResponse[0].averageDayForecastResponse.averageHumidity,
                uv = forecastResponse[0].averageDayForecastResponse.uv,
                chanceOfRain = forecastResponse[0].averageDayForecastResponse.chanceOfRain,
                weatherDescription = forecastResponse[0].averageDayForecastResponse.weatherDescription.description,
                iconPath = forecastResponse[0].averageDayForecastResponse.weatherDescription.iconUrl,
                forecast = forecastResponse.slice(1 until forecastResponse.size).map {
                    WeatherTime(
                        it.averageDayForecastResponse.averageTemperature,
                        it.averageDayForecastResponse.weatherDescription.iconUrl,
                        it.date
                    )
                }
            )
        }
    }


}