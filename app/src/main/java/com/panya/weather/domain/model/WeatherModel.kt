package com.panya.weather.domain.model

data class WeatherModel(
    val coordinates: CoordinatesModel,
    val weather: List<WeatherConditionModel>,
    val base: String,
    val temperature: TemperatureModel,
    val visibility: Int,
    val wind: WindModel,
    val clouds: CloudsModel,
    val date: Long,
    val system: SystemModel,
    val timezone: Long,
    val cityId: Int,
    val cityName: String,
    val responseCode: Int
)

data class CoordinatesModel(
    val longitude: Double,
    val latitude: Double
)

data class WeatherConditionModel(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class TemperatureModel(
    val temperature: Double,
    val feelsLike: Double,
    val minTemperature: Double,
    val maxTemperature: Double,
    val pressure: Int,
    val humidity: Int,
    val seaLevel: Int,
    val groundLevel: Int
)

data class WindModel(
    val speed: Double,
    val degree: Int,
    val gust: Double
)

data class CloudsModel(
    val all: Int
)

data class SystemModel(
    val type: Int,
    val id: Int,
    val country: String,
    val sunrise: Long,
    val sunset: Long
)
