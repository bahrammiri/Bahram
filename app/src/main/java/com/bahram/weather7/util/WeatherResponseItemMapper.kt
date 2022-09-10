package com.bahram.weather7.util

import android.content.Context
import com.bahram.weather7.model.*

class WeatherResponseItemMapper {
    companion object {
        fun loadCitiesItems(context: Context): ArrayList<CityItems>?{
            val sh = SharedPreferencesManager(context)
            val weatherResponses = sh.loadCitiesResponses()
            val citiesItems = ArrayList<CityItems>()
            weatherResponses.forEach { response ->
                citiesItems.add(CityItems(loadCityItems(response)))
            }
            return citiesItems
        }

        //    private fun loadCitiesItems() {
//        val sh = SharedPreferencesManager(this)
//        val weatherResponses = sh.loadCitiesResponses()
//        citiesItems = arrayListOf()
//        weatherResponses.forEach { response ->
//            val cityItems = arrayListOf<CityItem>()
//            val weatherResponseItemMapper = WeatherResponseItemMapper()
//            cityItems.add(CityItem(ViewType.ONE, weatherResponseItemMapper.createHeaderList(response)))
//            cityItems.add(CityItem(ViewType.TWO, weatherResponseItemMapper.createHoursList(response)))
//            cityItems.add(CityItem(ViewType.THREE, weatherResponseItemMapper.createDaysList(response)))
//            citiesItems?.add(CityItems(cityItems))
//        }
//    }

        fun loadCityItems(response: WeatherResponse?): java.util.ArrayList<CityItem> {
            val cityItems = arrayListOf<CityItem>()
            cityItems.add(CityItem(ViewType.ONE, createHeaderList(response)))
            cityItems.add(CityItem(ViewType.TWO, createHoursList(response)))
            cityItems.add(CityItem(ViewType.THREE, createDaysList(response)))
            return cityItems
        }

        private fun createHeaderList(response: WeatherResponse?): Header {
            val tempCurrent = response?.list?.getOrNull(0)?.main?.temp ?: 0.0
            val tempMin = response?.list?.getOrNull(0)?.main?.tempMin ?: 0.0
            val tempMax = response?.list?.getOrNull(0)?.main?.tempMax ?: 0.0

            return Header(response?.city?.name ?: "*",
                response?.city?.country ?: "*",
                tempCurrent.toString().substringBefore(".") + "°",
                response?.list?.getOrNull(0)?.weather?.getOrNull(0)?.description?.capitalize(),
                tempMin.toString().substringBefore(".") + "°",
                tempMax.toString().substringBefore(".") + "°")
        }

        private fun createHoursList(response: WeatherResponse?): ArrayList<Hours> {
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

        private fun createDaysList(response: WeatherResponse?): ArrayList<Days> {
            val daysList = arrayListOf<Days>()
            response?.list?.forEachIndexed { index, weatherList ->

                if ((index == 2) or (index == 9) or (index == 17) or (index == 25) or (index == 33)) {
                    val dayName = Util.timeStampToLocalDay(weatherList.date ?: 0)

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
}