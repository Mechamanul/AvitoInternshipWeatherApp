package com.mechamanul.avitointernshipweatherapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mechamanul.avitointernshipweatherapp.BuildConfig
import com.mechamanul.avitointernshipweatherapp.data.WeatherService
import com.mechamanul.avitointernshipweatherapp.utils.LocalDateDeserializer
import com.mechamanul.avitointernshipweatherapp.utils.LocalDateTimeDeserializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object WeatherModule {


    @Singleton
    @Provides
    fun provideGson(): Gson {
        val gsonBuilder =
            GsonBuilder().registerTypeAdapter(LocalDate::class.java, LocalDateDeserializer())
                .registerTypeAdapter(
                    LocalDateTime::class.java, LocalDateTimeDeserializer()
                ).setLenient()
        return gsonBuilder.create()

    }

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
    fun provideRetrofit(client: OkHttpClient, gson: Gson) =
        Retrofit.Builder().baseUrl("https://api.weatherapi.com/v1/").client(client)
            .addConverterFactory(
                GsonConverterFactory.create(gson)
            ).build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit) = retrofit.create(WeatherService::class.java)
}