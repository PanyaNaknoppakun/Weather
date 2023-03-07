package com.panya.weather.data.api

import com.panya.weather.data.model.WeatherResponseData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiInterface {
    @GET("/data/2.5/weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("appid") appid: String,
        @Query("units") units: String,
    ): Response<WeatherResponseData>
}
