package com.mechamanul.avitointernshipweatherapp.di

import com.mechamanul.avitointernshipweatherapp.data.WeatherRepositoryImpl
import com.mechamanul.avitointernshipweatherapp.domain.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bindRepositoryImpl(repositoryImpl: WeatherRepositoryImpl): WeatherRepository
}