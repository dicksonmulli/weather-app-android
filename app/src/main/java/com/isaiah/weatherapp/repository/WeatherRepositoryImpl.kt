package com.isaiah.weatherapp.repository
import com.isaiah.weatherapp.api.Resource
import com.isaiah.weatherapp.domain.data.WeatherResponse
import com.isaiah.weatherapp.domain.remote.WeatherRequest
import com.isaiah.weatherapp.domain.remote.WeatherService

class WeatherRepositoryImpl(
    private val weatherService: WeatherService
) : WeatherRepository, BaseRepo() {
    override suspend fun getWeather(params: WeatherRequest): Resource<WeatherResponse?> {
        return safeApiCall { weatherService.fetchWeather(params.lat.toString(), params.long.toString()) }
    }
}