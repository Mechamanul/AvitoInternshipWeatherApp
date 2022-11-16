package com.mechamanul.avitointernshipweatherapp.domain.usecases

import com.mechamanul.avitointernshipweatherapp.domain.WeatherRepository
import com.mechamanul.avitointernshipweatherapp.domain.common.ApiResult
import com.mechamanul.avitointernshipweatherapp.domain.model.DetailedDailyForecast
import javax.inject.Inject

class GetDailyForecastUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {
    suspend fun execute(cityName: String): ApiResult<DetailedDailyForecast> {
        return try {
            ApiResult.Success(weatherRepository.getDailyForecast(cityName))
        } catch (e: Exception) {
            ApiResult.Error(e)
        }
    }
}