package com.panya.weather.data.repository

import com.panya.weather.BuildConfig
import com.panya.weather.data.api.WeatherApiInterface
import com.panya.weather.data.model.WeatherResponseData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface WeatherRepository {
    fun getWeather(city: String): Flow<WeatherResponseData>
}

class WeatherRepositoryImpl(val weatherApiInterface: WeatherApiInterface) :
    WeatherRepository {
    companion object {
        private const val METRIC = "metric"
    }

    override fun getWeather(city: String): Flow<WeatherResponseData> {
        return flow {
            weatherApiInterface.getWeather(
                city = city,
                appid = BuildConfig.APP_ID,
                units = METRIC
            ).run {
                val responseBody = body()
                if (isSuccessful && responseBody != null) {
                    emit(responseBody)
                } else {
                    error(code())
                }
            }
        }
    }
}