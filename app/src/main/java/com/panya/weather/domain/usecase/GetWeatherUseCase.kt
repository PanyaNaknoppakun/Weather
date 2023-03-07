package com.panya.weather.domain.usecase

import com.panya.weather.data.repository.WeatherRepository
import com.panya.weather.domain.model.WeatherModel
import com.panya.weather.extension.toWeatherModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface GetWeatherUseCase {
    fun execute(city: String): Flow<WeatherModel>
}

class GetWeatherUseCaseImpl(private val weatherRepository: WeatherRepository) : GetWeatherUseCase {
    override fun execute(city: String): Flow<WeatherModel> {
        return weatherRepository.getWeather(city).map { _response ->
            _response.toWeatherModel()
        }
    }
}
