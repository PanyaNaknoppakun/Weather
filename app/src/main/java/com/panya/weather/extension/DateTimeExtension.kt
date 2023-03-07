package com.panya.weather.extension

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
fun Long.toTimePattern(timeZoneSec: Long): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this * 1000
    val timezoneOffset = timeZoneSec / 60
    val timezoneOffsetString = String.format("%+03d:%02d", timezoneOffset / 60, timezoneOffset % 60)
    val timeZone = TimeZone.getTimeZone("GMT$timezoneOffsetString")
    calendar.timeZone = timeZone
    val dateFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
    return dateFormat.format(calendar.time)
}

fun Long.isDay(timeZoneSec: Long): Boolean {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this * 1000
    val timezoneOffset = timeZoneSec / 60 // Convert offset to minutes
    val timezoneOffsetString = String.format("%+03d:%02d", timezoneOffset / 60, timezoneOffset % 60)
    val timeZone = TimeZone.getTimeZone("GMT$timezoneOffsetString")
    calendar.timeZone = timeZone
    val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
    return hourOfDay in 6..17
}