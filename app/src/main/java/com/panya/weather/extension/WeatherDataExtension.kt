package com.panya.weather.extension

import android.os.Build
import androidx.annotation.RequiresApi
import com.panya.weather.R
import com.panya.weather.data.model.Sys
import com.panya.weather.data.model.Weather
import com.panya.weather.data.model.WeatherResponseData
import com.panya.weather.data.model.Wind
import com.panya.weather.domain.model.*
import kotlin.math.roundToInt

private const val RAIN = "Rain"

fun WeatherResponseData.toWeatherModel(): WeatherModel {
    return WeatherModel(
        coordinates = CoordinatesModel(coordinates.longitude, coordinates.latitude),
        weather = weatherInfo.map { it.toWeatherConditionModel() },
        base = dataProvider,
        temperature = TemperatureModel(
            temperature = temperatureInfo.temperature,
            feelsLike = temperatureInfo.feelsLikeTemperature,
            minTemperature = temperatureInfo.minimumTemperature,
            maxTemperature = temperatureInfo.maximumTemperature,
            pressure = temperatureInfo.pressure,
            humidity = temperatureInfo.humidity,
            seaLevel = temperatureInfo.seaLevelPressure,
            groundLevel = temperatureInfo.groundLevelPressure
        ),
        visibility = visibilityDistance,
        wind = windInfo.toWindModel(),
        clouds = CloudsModel(cloudinessInfo.cloudinessPercentage),
        date = dateTime,
        system = systemInfo.toSystemModel(),
        timezone = timezoneOffset,
        cityId = cityId.toInt(),
        cityName = cityName,
        responseCode = statusCode
    )
}

fun Weather.toWeatherConditionModel(): WeatherConditionModel {
    return WeatherConditionModel(
        id = id,
        main = mainDescription,
        description = detailedDescription,
        icon = iconCode
    )
}

fun Wind.toWindModel(): WindModel {
    return WindModel(
        speed = speed,
        degree = direction,
        gust = gust
    )
}

fun Sys.toSystemModel(): SystemModel {
    return SystemModel(
        type = type,
        id = id.toInt(),
        country = countryCode,
        sunrise = sunriseTime,
        sunset = sunsetTime
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun WeatherModel.toWeatherPartialModel(): WeatherPartialModel {
    val title: Int
    val value: String
    if (date < system.sunrise) {
        title = R.string.sunrise
        value = system.sunrise.toTimePattern(timezone)
    } else {
        title = R.string.sunset
        value = system.sunset.toTimePattern(timezone)
    }
    val sunState = SunState(
        title = title,
        value = value
    )
    val iconState = when {
        weather.first().main == RAIN -> R.string.rain
        date.isDay(timezone) -> R.string.day
        else -> R.string.night
    }

    return WeatherPartialModel(
        humidity = temperature.humidity,
        cityName = cityName,
        weatherDesc = weather.first().description,
        temperatureMin = temperature.minTemperature.roundToInt(),
        temperatureMax = temperature.maxTemperature.roundToInt(),
        temperature = temperature.temperature.roundToInt(),
        syncedAt = date.toTimePattern(timezone),
        iconState = iconState,
        sunState = sunState
    )
}
