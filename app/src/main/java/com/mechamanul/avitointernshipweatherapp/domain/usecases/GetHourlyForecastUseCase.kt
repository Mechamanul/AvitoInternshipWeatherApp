package com.mechamanul.avitointernshipweatherapp.domain.usecases

import com.mechamanul.avitointernshipweatherapp.domain.WeatherRepository
import com.mechamanul.avitointernshipweatherapp.domain.common.ApiResult
import com.mechamanul.avitointernshipweatherapp.domain.model.DetailedHourlyForecast
import java.time.LocalTime
import javax.inject.Inject

class GetHourlyForecastUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {
    suspend fun execute(cityName: String): ApiResult<DetailedHourlyForecast> {
        return try {
            val apiResult = weatherRepository.getHourlyForecast(cityName)
            val filteredForecast =
                apiResult.forecast.filter { it.time.toLocalTime() > LocalTime.now() }
            ApiResult.Success(apiResult.copy(forecast = filteredForecast))
        } catch (e: Exception) {
            ApiResult.Error(e)
        }
    }
}