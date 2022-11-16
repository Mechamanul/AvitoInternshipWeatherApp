package com.mechamanul.avitointernshipweatherapp.domain.common


sealed class ApiResult<out T> {
    class Success<out T>(val data: T) : ApiResult<T>()
    class Error(val exception: Exception) : ApiResult<Nothing>()
}