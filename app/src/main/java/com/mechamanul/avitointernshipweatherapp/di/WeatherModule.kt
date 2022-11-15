package com.mechamanul.avitointernshipweatherapp.di

import com.example.weatherapp.data.WeatherService
import com.mechamanul.avitointernshipweatherapp.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object WeatherModule {
    @Singleton
    @Provides
    fun provideOkHTTPClient(): OkHttpClient {
        val interceptor = Interceptor { chain ->
            val original = chain.request()
            val key = BuildConfig.WEATHER_API
            val url = original.url().newBuilder().addQueryParameter("key", key).build()
            chain.proceed(original.newBuilder().url(url).build())
        }
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient) =
        Retrofit.Builder().baseUrl("https://api.weatherapi.com/v1/").client(client)
            .addConverterFactory(
                GsonConverterFactory.create()
            ).build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit) = retrofit.create(WeatherService::class.java)
}