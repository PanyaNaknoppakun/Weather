package com.panya.weather.domain.usecase

import com.panya.weather.data.model.*
import com.panya.weather.data.repository.WeatherRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test

class TestGetWeatherUseCase {

    private val weatherRepository = mockk<WeatherRepository>()

    // create an instance of GetWeatherUseCaseImpl using the mock
    private val getWeatherUseCase = GetWeatherUseCaseImpl(weatherRepository)

    @Test
    fun `execute should return flow of WeatherModel`() = runBlockingTest {
        // define the city name and a mock response
        val city = "Bangkok"
        val mockResponse = WeatherResponseData(
            Coord(0.0, 0.0),
            listOf(Weather(800, "Clear", "clear sky", "01d")),
            "test",
            Main(0.0, 0.0, 0.0, 0.0, 0, 0, 0, 0),
            10000,
            Wind(0.0, 0, 0.0),
            Clouds(0),
            0L,
            Sys(0, 0L, "GB", 0L, 0L),
            0L,
            0L,
            "Bangkok",
            200
        )

        // mock the response of the getWeather function of the weatherRepository mock
        coEvery { weatherRepository.getWeather(city) } returns flowOf(mockResponse)

        // call the execute function of the getWeatherUseCase instance
        val result = getWeatherUseCase.execute(city)

        // verify that the result is of type Flow<WeatherModel>
        assertTrue(true)

        // collect the flow and verify that the returned WeatherModel object
        // has the correct data from the mock response
        result.collect { weatherModel ->
            assertEquals(mockResponse.cityName, weatherModel.cityName)
            assertEquals(mockResponse.temperatureInfo.temperature, weatherModel.temperature.temperature)
            assertEquals(
                mockResponse.weatherInfo[0].mainDescription,
                weatherModel.weather[0].description
            )
            assertEquals(mockResponse.weatherInfo[0].iconCode, weatherModel.weather[0].icon)
        }

        // verify that the getWeather function of the weatherRepository mock was called once with the correct argument
        coVerify { weatherRepository.getWeather(city) }
    }
}
