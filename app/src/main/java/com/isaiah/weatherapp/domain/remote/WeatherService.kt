package com.isaiah.weatherapp.domain.remote

import com.isaiah.weatherapp.domain.data.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("/data/2.5/weather")
    suspend fun fetchWeather(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("appid") apiKey: String = "d206ce58c2653ee47ddde82e33af5e7f"
    ): Response<WeatherResponse?>
}
