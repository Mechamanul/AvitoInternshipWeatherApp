package com.mechamanul.avitointernshipweatherapp.data

import android.location.Location
import com.mechamanul.avitointernshipweatherapp.data.remote.WeatherService
import com.mechamanul.avitointernshipweatherapp.domain.AppRepository
import com.mechamanul.avitointernshipweatherapp.domain.LocationService
import com.mechamanul.avitointernshipweatherapp.domain.model.DayForecastDetails
import com.mechamanul.avitointernshipweatherapp.domain.model.DetailedForecast
import com.mechamanul.avitointernshipweatherapp.domain.model.WeatherTime
import com.mechamanul.avitointernshipweatherapp.domain.model.WeekForecastDetails
import java.time.LocalDateTime
import javax.inject.Inject
import com.mechamanul.avitointernshipweatherapp.data.remote.model.City as DataLocation
import com.mechamanul.avitointernshipweatherapp.domain.model.City as DomainCity

class AppRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService,
    private val locationService: LocationService,
//    private val sharedPreferences: SharedPreferences
) : AppRepository {


    override suspend fun getSuggestedCitiesList(query: String): List<DomainCity> {
        return weatherService.getSuggestedCitiesList(query).map {
            it.mapToDomain()
        }
    }

    private fun DataLocation.mapToDomain() = DomainCity(id = id, name = name, country = country)

//    override suspend fun getHourlyForecast(query: String): DayForecastDetails {
//        val apiResponse = weatherService.getHourlyForecast(query)
//        return with(apiResponse) {
//            DayForecastDetails(
//                temperature = currentHourForecast.temperature,
//                windSpeed = currentHourForecast.windSpeed,
//                humidity = currentHourForecast.humidity,
//                uv = currentHourForecast.uv,
//                feelsLikeTemperature = currentHourForecast.feelsLikeTemperature,
//                weatherDescription = currentHourForecast.weatherDescription.description,
//                iconPath = appendHttps(currentHourForecast.weatherDescription.iconUrl),
//                forecast = forecast.forecastResponse[0].listOfHourlyForecast.map {
//                    WeatherTime(
//                        it.temperature,
//                        appendHttps(it.weatherDescription.iconUrl),
//                        it.time
//                    )
//                }, city = city.mapToDomain(), time = LocalDateTime.now()
//
//            )
//        }
//    }

    private fun appendHttps(url: String) = "https:${url}"
    override suspend fun getDetailedForecast(query: String): DetailedForecast {
        val apiResponse = weatherService.getDailyForecast(query)
        val weekForecastDetails = with(apiResponse.forecast) {
            WeekForecastDetails(
                avgTemperature = forecastResponse[0].averageDayForecastResponse.averageTemperature,
                maxWindSpeed = forecastResponse[0].averageDayForecastResponse.maxWind,
                avgHumidity = forecastResponse[0].averageDayForecastResponse.averageHumidity,
                uv = forecastResponse[0].averageDayForecastResponse.uv,
                chanceOfRain = forecastResponse[0].averageDayForecastResponse.chanceOfRain,
                weatherDescription = forecastResponse[0].averageDayForecastResponse.weatherDescription.description,
                iconPath = appendHttps(forecastResponse[0].averageDayForecastResponse.weatherDescription.iconUrl),
            )

        }
        val dayForecastDetails = with(apiResponse.currentHourForecast) {
            DayForecastDetails(
                temperature = temperature,
                windSpeed = windSpeed,
                humidity = humidity,
                uv = uv,
                feelsLikeTemperature = feelsLikeTemperature,
                weatherDescription = weatherDescription.description,
                iconPath = appendHttps(weatherDescription.iconUrl),
            )
        }
        val dayForecast =
            apiResponse.forecast.forecastResponse.take(2).flatMap {
                it.listOfHourlyForecast.map { hourForecast ->
                    WeatherTime(
                        hourForecast.temperature,
                        appendHttps(hourForecast.weatherDescription.iconUrl),
                        hourForecast.time
                    )
                }
            }
        val weekForecast = with(apiResponse.forecast) {
            forecastResponse.slice(1 until forecastResponse.size)
                .map {
                    WeatherTime(
                        it.averageDayForecastResponse.averageTemperature,
                        appendHttps(it.averageDayForecastResponse.weatherDescription.iconUrl),
                        it.date.atStartOfDay()
                    )
                }
        }
        return DetailedForecast(
            LocalDateTime.now(),
            apiResponse.city.mapToDomain(),
            weekForecastDetails,
            dayForecastDetails,
            dayForecast,
            weekForecast
        )
    }

//    override suspend fun getDailyForecast(query: String): WeekForecastDetails {
//        val apiResponse = weatherService.getDailyForecast(query)
//        return with(apiResponse) {
//            WeekForecastDetails(
//                avgTemperature = forecast.forecastResponse[0].averageDayForecastResponse.averageTemperature,
//                maxWindSpeed = forecast.forecastResponse[0].averageDayForecastResponse.maxWind,
//                avgHumidity = forecast.forecastResponse[0].averageDayForecastResponse.averageHumidity,
//                uv = forecast.forecastResponse[0].averageDayForecastResponse.uv,
//                chanceOfRain = forecast.forecastResponse[0].averageDayForecastResponse.chanceOfRain,
//                weatherDescription = forecast.forecastResponse[0].averageDayForecastResponse.weatherDescription.description,
//                iconPath = appendHttps(forecast.forecastResponse[0].averageDayForecastResponse.weatherDescription.iconUrl),
//                forecast = forecast.forecastResponse.slice(1 until forecast.forecastResponse.size)
//                    .map {
//                        WeatherTime(
//                            it.averageDayForecastResponse.averageTemperature,
//                            appendHttps(it.averageDayForecastResponse.weatherDescription.iconUrl),
//                            it.date.atStartOfDay()
//                        )
//                    },
//                city = city.mapToDomain(),
//                time = LocalDateTime.now()
//            )
//        }
//    }

    override suspend fun getCurrentLocation(): Location? {
        return locationService.getCurrentLocation()
    }

//    override suspend fun getSavedCityName(): String? {
//        return sharedPreferences.getString("current_city_name", "London")
//    }
//
//    override suspend fun setSavedCityName(name: String) {
//        sharedPreferences.edit().putString("current_city_name", name).apply()
//    }


}