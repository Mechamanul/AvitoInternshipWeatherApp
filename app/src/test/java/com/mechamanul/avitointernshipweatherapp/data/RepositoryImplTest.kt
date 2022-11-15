package com.mechamanul.avitointernshipweatherapp.data

import com.example.weatherapp.data.RepositoryImpl
import com.example.weatherapp.data.WeatherService
import com.mechamanul.avitointernshipweatherapp.BuildConfig
import com.mechamanul.avitointernshipweatherapp.domain.Repository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val DEFAULT_CITY = "London"

@ExperimentalCoroutinesApi
class RepositoryImplTest {

    @Before
    fun setUp() {
    }


    @Test
    suspend fun getHourlyForecast() {
    }

    @Test
    fun getDailyForecast() {
    }
}