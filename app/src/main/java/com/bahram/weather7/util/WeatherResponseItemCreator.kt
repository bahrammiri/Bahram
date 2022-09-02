package com.bahram.weather7.util

import android.os.Build
import androidx.annotation.RequiresApi
import com.bahram.weather7.model.Days
import com.bahram.weather7.model.Header
import com.bahram.weather7.model.Hours
import com.bahram.weather7.model.WeatherResponse
import java.util.*
import kotlin.collections.ArrayList

class WeatherResponseItemCreator {

    fun createHeaderList(response: WeatherResponse?): Header {
        var tempCurrent = response?.list?.getOrNull(0)?.main?.temp ?: 0.0
        var tempMin = response?.list?.getOrNull(0)?.main?.tempMin ?: 0.0
        var tempMax = response?.list?.getOrNull(0)?.main?.tempMax ?: 0.0

        return Header(response?.city?.name ?: "*",
            response?.city?.country ?: "*",
            tempCurrent.toString().substringBefore(".") + "°",
            response?.list?.getOrNull(0)?.weather?.getOrNull(0)?.description?.capitalize(),
            tempMin.toString().substringBefore(".") + "°",
            tempMax.toString().substringBefore(".") + "°")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createHoursList(response: WeatherResponse?): ArrayList<Hours> {
        val hoursList = arrayListOf<Hours>()
        response?.list?.forEach {
            val hour = Util.timeStampToLocalHour(it.date ?: 0)
            val iconCode = it.weather?.getOrNull(0)?.icon
            val url = "https://openweathermap.org/img/wn/$iconCode@2x.png"
            val temp = it.main?.temp ?: 0.0
            hoursList.add(Hours(hour, url, temp.toString().substringBefore(".") + "°"))
        }
        return hoursList
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createDaysList(response: WeatherResponse?): ArrayList<Days> {
        val daysList = arrayListOf<Days>()
        response?.list?.forEachIndexed { index, weatherList ->

            if ((index == 2) or (index == 9) or (index == 17) or (index == 25) or (index == 33)) {
                val dayName = Util.timeStampToLocalDay(weatherList.date ?: 0).substring(0, 3)
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                val iconCode = weatherList.weather?.getOrNull(0)?.icon
                val url = "https://openweathermap.org/img/wn/$iconCode@2x.png"

                val tempMin3 = weatherList.main?.tempMin ?: 0.0

                val tempMax3 = weatherList.main?.tempMax ?: 0.0
                daysList.add(Days(dayName,
                    url,
                    tempMin3.toString().substringBefore(".") + "°",
                    tempMax3.toString().substringBefore(".") + "°"))
            }
        }
        return daysList
    }
}