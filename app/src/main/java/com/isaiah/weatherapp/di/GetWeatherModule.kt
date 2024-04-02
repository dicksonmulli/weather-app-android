package ke.co.equitybank.oneequity.features.borrow.get_loan.di

import com.isaiah.weatherapp.domain.remote.WeatherService
import com.isaiah.weatherapp.repository.WeatherRepository
import com.isaiah.weatherapp.repository.WeatherRepositoryImpl
import com.isaiah.weatherapp.ui.home.HomeViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object GetWeatherModule {

    val module = module {
        single<WeatherService> {
            val retrofit = RetrofitClient.getClient()
            retrofit.create(WeatherService::class.java)
        }

        single<WeatherRepository> {
            WeatherRepositoryImpl(weatherService = get())
        }

        viewModel { HomeViewModel(get()) }
    }
}

private val loggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY // Set the desired logging level
}

private val client = OkHttpClient.Builder()
    .addInterceptor(loggingInterceptor)
    .build()

object RetrofitClient {
    private const val BASE_URL = "https://api.openweathermap.org"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory()) // Add this line to support Kotlin data classes
        .build()

    fun getClient(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
}
