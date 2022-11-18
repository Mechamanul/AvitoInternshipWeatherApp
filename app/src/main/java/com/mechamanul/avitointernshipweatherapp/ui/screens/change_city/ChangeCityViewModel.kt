package com.mechamanul.avitointernshipweatherapp.ui.screens.change_city

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mechamanul.avitointernshipweatherapp.domain.AppRepository
import com.mechamanul.avitointernshipweatherapp.domain.common.ApiResult
import com.mechamanul.avitointernshipweatherapp.domain.model.City
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias SuggestionsResult = ApiResult<List<City>>


@HiltViewModel
class ChangeCityViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private val _suggestionsList: MutableStateFlow<SuggestionsResult> =
        MutableStateFlow(ApiResult.Success(emptyList()))
    val suggestionsList: StateFlow<SuggestionsResult> = _suggestionsList

    fun searchCity(query: String) = viewModelScope.launch {
        try {
            _suggestionsList.value = ApiResult.Success(repository.getSuggestedCitiesList(query))
        } catch (e: Exception) {
            _suggestionsList.value = ApiResult.Error(e)
        }

    }

}