package com.panya.weather.data.model

import com.google.gson.annotations.SerializedName

data class WeatherResponseData(
    @SerializedName("coord") val coordinates: Coord,
    @SerializedName("weather") val weatherInfo: List<Weather>,
    @SerializedName("base") val dataProvider: String,
    @SerializedName("main") val temperatureInfo: Main,
    @SerializedName("visibility") val visibilityDistance: Int,
    @SerializedName("wind") val windInfo: Wind,
    @SerializedName("clouds") val cloudinessInfo: Clouds,
    @SerializedName("dt") val dateTime: Long,
    @SerializedName("sys") val systemInfo: Sys,
    @SerializedName("timezone") val timezoneOffset: Long,
    @SerializedName("id") val cityId: Long,
    @SerializedName("name") val cityName: String,
    @SerializedName("cod") val statusCode: Int
)

data class Coord(
    @SerializedName("lon") val longitude: Double,
    @SerializedName("lat") val latitude: Double
)

data class Weather(
    @SerializedName("id") val id: Int,
    @SerializedName("main") val mainDescription: String,
    @SerializedName("description") val detailedDescription: String,
    @SerializedName("icon") val iconCode: String
)

data class Main(
    @SerializedName("temp") val temperature: Double,
    @SerializedName("feels_like") val feelsLikeTemperature: Double,
    @SerializedName("temp_min") val minimumTemperature: Double,
    @SerializedName("temp_max") val maximumTemperature: Double,
    @SerializedName("pressure") val pressure: Int,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("sea_level") val seaLevelPressure: Int,
    @SerializedName("grnd_level") val groundLevelPressure: Int
)

data class Wind(
    @SerializedName("speed") val speed: Double,
    @SerializedName("deg") val direction: Int,
    @SerializedName("gust") val gust: Double
)

data class Clouds(
    @SerializedName("all") val cloudinessPercentage: Int
)

data class Sys(
    @SerializedName("type") val type: Int,
    @SerializedName("id") val id: Long,
    @SerializedName("country") val countryCode: String,
    @SerializedName("sunrise") val sunriseTime: Long,
    @SerializedName("sunset") val sunsetTime: Long
)
