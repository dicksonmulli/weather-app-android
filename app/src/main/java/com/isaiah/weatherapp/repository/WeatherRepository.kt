package com.isaiah.weatherapp.repository

import com.isaiah.weatherapp.api.ApiResponse
import com.isaiah.weatherapp.api.Resource
import com.isaiah.weatherapp.domain.data.WeatherResponse
import com.isaiah.weatherapp.domain.remote.WeatherRequest

interface WeatherRepository {

    suspend fun getWeather(params: WeatherRequest): Resource<WeatherResponse?>
}