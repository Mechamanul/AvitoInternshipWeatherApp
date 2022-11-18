package com.mechamanul.avitointernshipweatherapp.di

import com.mechamanul.avitointernshipweatherapp.data.location.DefaultLocationProvider
import com.mechamanul.avitointernshipweatherapp.domain.LocationService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class LocationModule {
    @Binds
    abstract fun bindDefaultLocationProvider(defaultLocationProvider: DefaultLocationProvider): LocationService
}