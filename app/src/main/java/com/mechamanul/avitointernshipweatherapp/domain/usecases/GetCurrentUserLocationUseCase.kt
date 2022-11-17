package com.mechamanul.avitointernshipweatherapp.domain.usecases

import android.location.Location
import android.util.Log
import com.mechamanul.avitointernshipweatherapp.domain.LocationService
import javax.inject.Inject

class GetCurrentUserLocationUseCase @Inject constructor(private val locationService: LocationService) {
    suspend fun execute(): Location? {
        val location = locationService.getCurrentLocation()
        Log.d("Latitude", "${location!!.latitude}")
        Log.d("Latitude", "${location!!.longitude}")
        return location
    }
}