package com.panya.weather.di

import com.panya.weather.data.repository.WeatherRepository
import com.panya.weather.data.repository.WeatherRepositoryImpl
import com.panya.weather.domain.usecase.GetWeatherUseCase
import com.panya.weather.domain.usecase.GetWeatherUseCaseImpl
import com.panya.weather.presentation.viewmodel.MainViewModel
import org.koin.dsl.module

val weatherModule = module {
    // repository
    factory<WeatherRepository> {
        WeatherRepositoryImpl(
            weatherApiInterface = get()
        )
    }

    // usecase
    factory<GetWeatherUseCase> {
        GetWeatherUseCaseImpl(
            weatherRepository = get()
        )
    }

    // viewModel
    factory {
        MainViewModel(
            getWeatherUseCase = get()
        )
    }


}.plus(
    listOf(networkModule)
)
