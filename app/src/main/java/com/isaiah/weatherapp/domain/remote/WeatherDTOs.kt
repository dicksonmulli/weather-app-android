package com.isaiah.weatherapp.domain.remote

import android.os.Parcelable
import androidx.annotation.Keep

@Keep
data class WeatherRequest(
    val long: Double,
    val lat: Double
)
//    : Parcelable

//@Keep
//data class WeatherResponse(
//    val coordinates: Coord?,
//    val weather: List<WeatherDetails?>
//)
//    : Parcelable

//@Keep
//@Parcelize
data class Coordinates(
    val long: Double?,
    val lat: Double?
)
//    : Parcelable

//@Keep
//@Parcelize
data class WeatherDetails(
    val id: Int?,
    val main: String?,
    val description: String?,
    val icon: String?
)
//    : Parcelable

data class ExampleErrorResponse(
    val failureMessage: String?
)