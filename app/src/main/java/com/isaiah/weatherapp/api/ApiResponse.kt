package com.isaiah.weatherapp.api

import com.isaiah.weatherapp.domain.data.WeatherResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiResponse(
    @Json(name = "responseObject")
    val responseObject: WeatherResponse?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "code")
    val statusCode: String? ,
    @Json(name = "message")
    val statusMessage: String?,
    @Json(name = "errorBody")
    val error: String?
)
