package com.mechamanul.avitointernshipweatherapp.ui

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mechamanul.avitointernshipweatherapp.domain.AppRepository
import com.mechamanul.avitointernshipweatherapp.domain.common.ApiResult
import com.mechamanul.avitointernshipweatherapp.domain.model.DetailedForecast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject


typealias DetailedForecastApiResult = ApiResult<DetailedForecast>

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AppRepository,
) :
    ViewModel() {
    private val _detailedForecast: MutableStateFlow<DetailedForecastApiResult?> =
        MutableStateFlow(null)
    val detailedForecast: StateFlow<DetailedForecastApiResult?> = _detailedForecast

    fun setLocation(query: String?) = viewModelScope.launch {
        try {
            if (query != null) {
                val apiResult = repository.getDetailedForecast(query)
                val currentTime = LocalDateTime.now()
                val formattedForecast =
                    apiResult.copy(dayForecast = apiResult.dayForecast.filter {
                        it.time > currentTime && currentTime.plusHours(24L) > it.time
                    })
                _detailedForecast.value = ApiResult.Success(formattedForecast)
            }
        } catch (e: Exception) {
            _detailedForecast.value = ApiResult.Error(e)
        }
    }

}