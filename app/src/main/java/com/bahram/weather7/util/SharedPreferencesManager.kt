package com.bahram.weather7.util

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.bahram.weather7.model.WeatherResponse
import com.google.gson.Gson

class SharedPreferencesManager(context: Context) {

    var sharedPreferences: SharedPreferences

    init {
        sharedPreferences =
            context.getSharedPreferences("sharedResponse", AppCompatActivity.MODE_PRIVATE)
    }

    fun saveCityResponse(cityName: String, weatherResponse: WeatherResponse) {
        sharedPreferences.edit().putString(cityName, Gson().toJson(weatherResponse)).apply()
    }

    fun loadCityResponse(cityName: String): WeatherResponse {
        val weatherResponseString = sharedPreferences.getString(cityName, null)
        return Gson().fromJson(weatherResponseString, WeatherResponse::class.java)
    }

    fun loadCitiesResponses(): ArrayList<WeatherResponse> {
        val arrayList = ArrayList<WeatherResponse>()
        sharedPreferences.all.forEach {
            arrayList.add(loadCityResponse(it.key))
        }
        return arrayList
    }

}