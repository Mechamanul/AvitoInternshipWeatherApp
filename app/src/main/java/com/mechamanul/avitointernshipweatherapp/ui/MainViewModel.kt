package com.mechamanul.avitointernshipweatherapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mechamanul.avitointernshipweatherapp.domain.common.ApiResult
import com.mechamanul.avitointernshipweatherapp.domain.model.DetailedDailyForecast
import com.mechamanul.avitointernshipweatherapp.domain.model.DetailedHourlyForecast
import com.mechamanul.avitointernshipweatherapp.domain.usecases.GetDailyForecastUseCase
import com.mechamanul.avitointernshipweatherapp.domain.usecases.GetHourlyForecastUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias DetailedDailyForecastResult = ApiResult<DetailedDailyForecast>
typealias DetailedHourlyForecastApiResult = ApiResult<DetailedHourlyForecast>


const val CITY_NAME = "Pavlodar"

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getDailyForecastUseCase: GetDailyForecastUseCase,
    private val getHourlyForecastUseCase: GetHourlyForecastUseCase
) :
    ViewModel() {
    private val _dailyForecast: MutableSharedFlow<DetailedDailyForecastResult> =
        MutableSharedFlow(replay = 1)
    val dailyForecast: SharedFlow<DetailedDailyForecastResult> = _dailyForecast
    private val _hourlyForecast: MutableSharedFlow<DetailedHourlyForecastApiResult> =
        MutableSharedFlow(replay = 1)
    val hourlyForecast: SharedFlow<DetailedHourlyForecastApiResult> = _hourlyForecast

    init {
        viewModelScope.launch {
            _hourlyForecast.emit(getHourlyForecastUseCase.execute(CITY_NAME))
            _dailyForecast.emit(getDailyForecastUseCase.execute(CITY_NAME))
        }
    }

}