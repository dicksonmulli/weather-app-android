package com.isaiah.weatherapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isaiah.weatherapp.R
import com.isaiah.weatherapp.domain.remote.Coordinates
import com.isaiah.weatherapp.domain.remote.WeatherDetails
import com.isaiah.weatherapp.domain.remote.WeatherRequest
import com.isaiah.weatherapp.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _uiState = MutableLiveData<WeatherUiState>()
    val uiState: LiveData<WeatherUiState> = _uiState

    init {
        // Calling this function in init{}
        // block so that it'll automatically
        // get called on initialization of viewmodel
        fetchWeatherCoordinates()
    }

    val defaultLongitude = -94.04
    val defaultLatitude = 33.44

    private val _text = MutableLiveData<String>().apply {
        value = "Fetching weather for lat: $defaultLatitude & long: $defaultLongitude"
    }
    val text: LiveData<String> = _text

    fun fetchWeatherCoordinates() {
        _uiState.value = WeatherUiState.Loading

        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                weatherRepository.getWeather(
                    WeatherRequest(
                        defaultLongitude,
                        defaultLatitude
                    )
                )
            }

            response?.let { response ->
                response.data?.let { weatherData ->
                    val coords = weatherData.coord

                    _uiState.value =
                        WeatherUiState.CoordinatesDetails(
                            coordinates = Coordinates(
                                coords?.lat,
                                coords?.lon
                            )
                        )
                }

                response.message?.let {
                    _uiState.value = WeatherUiState.Error(message = it ?: R.string.unknown_error)
                }
            }
        }
    }

    fun fetchWeatherDetails() {
        _uiState.value = WeatherUiState.Loading
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                weatherRepository.getWeather(
                    WeatherRequest(
                        defaultLongitude,
                        defaultLatitude
                    )
                )
            }

            response?.let { response ->
                response.data?.let { weatherData ->
                    val weather = weatherData.weather?.firstOrNull()

                    _uiState.value =
                       if(weather != null)  {
                            WeatherUiState.Weather(
                                weatherDetails = WeatherDetails(
                                    weather?.id,
                                    weather?.main,
                                    weather?.description,
                                    weather?.icon
                                )
                            )
                        } else{
                           WeatherUiState.WeatherEmpty
                       }
                }

                response.message?.let {
                    _uiState.value = WeatherUiState.Error(message = it ?: R.string.unknown_error)
                }
            }
        }
    }
}

sealed class WeatherUiState {
    object Loading : WeatherUiState()

    object Success : WeatherUiState()

    data class CoordinatesDetails(val coordinates: Coordinates?) : WeatherUiState()

    data class CoordinatesDetailsError(val message: String) : WeatherUiState()

    data class Weather(val weatherDetails: WeatherDetails?) : WeatherUiState()
    object WeatherEmpty : WeatherUiState()

    data class WeatherDetailsError(val message: String) : WeatherUiState()

    data class Error(val title: Int = R.string.something_went_wrong, val message: Any) :
        WeatherUiState()
}