package com.panya.weather.data.repository

import com.panya.weather.data.api.WeatherApiInterface
import com.panya.weather.data.model.*
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TestWeatherRepository {
    private lateinit var weatherApiInterface: WeatherApiInterface
    private lateinit var weatherRepository: WeatherRepositoryImpl

    @Before
    fun setUp() {
        weatherApiInterface = mockk()
        weatherRepository = WeatherRepositoryImpl(weatherApiInterface)
    }

    @Test
    fun `getWeather should return flow of WeatherResponseData`() = runBlockingTest {
        // given
        val city = "Bangkok"
        val weatherResponseData = WeatherResponseData(
            coordinates = Coord(13.75, 100.5167),
            weatherInfo = listOf(Weather(800, "Clear", "clear sky", "01n")),
            dataProvider = "stations",
            temperatureInfo = Main(25.0, 25.0, 25.0, 25.0, 1007, 83, 1010, 1009),
            visibilityDistance = 10000,
            windInfo = Wind(2.57, 120, 2.81),
            cloudinessInfo = Clouds(0),
            dateTime = 1646717497,
            systemInfo = Sys(1, 9114, "TH", 1646668434, 1646712934),
            timezoneOffset = 25200,
            cityId = 1609350,
            cityName = "Bangkok",
            statusCode = 200
        )
        coEvery {
            weatherApiInterface.getWeather(
                city = any(),
                appid = any(),
                units = "metric"
            )
        } returns mockk {
            every { isSuccessful } returns true
            every { body() } returns weatherResponseData
        }

        // when
        val result = mutableListOf<WeatherResponseData>()
        weatherRepository.getWeather(city).collect { result.add(it) }

        // then
        assertEquals(listOf(weatherResponseData), result)
    }

    @Test(expected = Exception::class)
    fun `getWeather should throw exception when API response is not successful`() =
        runBlockingTest {
            // given
            val city = "Bangkok"
            val code = 500
            coEvery {
                weatherApiInterface.getWeather(
                    city = any(),
                    appid = any(),
                    units = "metric"
                )
            } returns mockk {
                every { isSuccessful } returns false
                every { code() } returns code
            }

            // when
            weatherRepository.getWeather(city).collect()

            // then
            // expect exception to be thrown
        }
}
