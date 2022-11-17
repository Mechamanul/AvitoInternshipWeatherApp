package com.mechamanul.avitointernshipweatherapp.domain

import android.location.Location

interface LocationService {
    suspend fun getCurrentLocation(): Location?
}
