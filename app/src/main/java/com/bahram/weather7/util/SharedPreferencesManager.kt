package com.bahram.weather7.util

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity

class SharedPreferencesManager(context: Context) {

    data class CityName(
        var cityNameSelected: String,
    )

    var sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences("selectedCities", AppCompatActivity.MODE_PRIVATE)
    }

    fun saveCityName(cityNameKey: String, cityNameSelected: String) {
//        sharedPreferences.edit().putString(cityNameKey, Gson().toJson(cityNameSelected)).apply()
        sharedPreferences.edit().putString(cityNameKey, cityNameSelected).apply()

    }

    fun loadCityName(cityNameKey: String): String {
        val cityString = sharedPreferences.getString(cityNameKey, null)
        return cityString.toString()
    }

    fun removeCityName(cityNameKey: String) {
        sharedPreferences.edit().remove(cityNameKey).apply()
    }

    fun loadCities(): ArrayList<CityName> {
        val arrayList = ArrayList<CityName>()
        sharedPreferences.all.forEach {
//            arrayList.add(loadCityName(it.key))
            arrayList.add(CityName(loadCityName(it.key)))
        }
        return arrayList
    }

//    fun saveCityResponse(cityName: String, weatherResponse: WeatherResponse) {
//        sharedPreferences.edit().putString(cityName, Gson().toJson(weatherResponse)).apply()
//    }
//
//    private fun loadCityResponse(cityName: String): WeatherResponse {
//        val weatherResponseString = sharedPreferences.getString(cityName, null)
//        return Gson().fromJson(weatherResponseString, WeatherResponse::class.java)
//    }
//
//    fun loadCitiesResponses(): ArrayList<WeatherResponse> {
//        val arrayList = ArrayList<WeatherResponse>()
//        sharedPreferences.all.forEach {
//            arrayList.add(loadCityResponse(it.key))
//        }
//        return arrayList
//    }

}