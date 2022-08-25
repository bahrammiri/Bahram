package com.bahram.weather7.retrofit

import android.content.Context
import com.bahram.weather7.model.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("forecast")
    fun getCityWeatherData(
        @Query("q") cityName: String,
        @Query("appid") api_key: String,
        @Query("units") units: String,
    ): Call<WeatherResponse>
}

//fun getAllValues(context: Context?): List<*>? {
//    val values: Map<*, *> = getPref(context).getAll()
//    val prefDataList: MutableList<*> = ArrayList()
//    for ((key, value): Map.Entry<*, *> in values) {
//        val prefData = PrefData()
//        prefData.key = key
//        prefData.value = value.toString()
//        prefDataList.add(prefData)
//    }
//    return prefDataList
//}
//
//
//data class PrefData (
//    var key: String? = null,
//    var value: String? = null
//)