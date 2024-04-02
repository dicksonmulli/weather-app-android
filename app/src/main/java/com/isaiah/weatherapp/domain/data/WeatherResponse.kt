package com.isaiah.weatherapp.domain.data


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherResponse(
    @Json(name = "coord")
    val coord: Coord?,
    @Json(name = "weather")
    val weather: List<WeatherX>?,

)