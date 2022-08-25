package com.bahram.weather7

import android.os.Build
import androidx.annotation.RequiresApi
import com.bahram.weather7.model.Days
import com.bahram.weather7.model.Header
import com.bahram.weather7.model.Hours
import com.bahram.weather7.model.WeatherResponse

class WeatherResponseConvertorToC {

    fun createHeaderList(response: WeatherResponse?): Header {
        val tempCurrent = response?.list?.getOrNull(0)?.main?.temp ?: 0.0
        val tempMin1 = response?.list?.getOrNull(0)?.main?.tempMin ?: 0.0
        val tempMax1 = response?.list?.getOrNull(0)?.main?.tempMax ?: 0.0

        return Header(response?.city?.name ?: "*",
            response?.city?.country ?: "*",
            tempCurrent.toString().substringBefore(".") + "°",
            response?.list?.getOrNull(0)?.weather?.getOrNull(0)?.description?.capitalize(),
            tempMin1.toString().substringBefore(".") + "°",
            tempMax1.toString().substringBefore(".") + "°")
    }


    fun createHoursList(response: WeatherResponse?): ArrayList<Hours> {
        val hoursList = arrayListOf<Hours>()
        response?.list?.forEach {
//            val hour2 = Util.timeStampToLocalHour(it.date ?: 0)
            val hour2 = "dddd"

            val iconCode = it.weather?.getOrNull(0)?.icon
            val url = "https://openweathermap.org/img/wn/$iconCode@2x.png"
            val temp2 = it.main?.temp ?: 0.0
            hoursList.add(Hours(hour2, url, temp2.toString().substringBefore(".") + "°"))
        }
        return hoursList
    }

    fun createDaysList(response: WeatherResponse?): ArrayList<Days> {
        val daysList = arrayListOf<Days>()
        response?.list?.forEachIndexed { index, weatherList ->

            if ((index == 2) or (index == 9) or (index == 17) or (index == 25) or (index == 33)) {
//                val dayName =
//                    Util.timeStampToLocalDay(weatherList.date ?: 0).substring(0, 3).capitalize()
                val dayName = "222"
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