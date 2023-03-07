package com.panya.weather.domain.model

import androidx.annotation.StringRes
import com.panya.weather.R

data class WeatherPartialModel(
    val humidity: Int,
    val sunState: SunState,
    val syncedAt: String,
    val cityName: String,
    val weatherDesc: String,
    val temperatureMin: Int,
    val temperatureMax: Int,
    val temperature: Int,
    @StringRes val iconState: Int = R.string.day
)

data class SunState(
    @StringRes val title: Int,
    val value: String
)
