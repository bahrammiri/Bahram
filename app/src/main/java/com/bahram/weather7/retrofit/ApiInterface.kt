package com.bahram.weather7.retrofit

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
