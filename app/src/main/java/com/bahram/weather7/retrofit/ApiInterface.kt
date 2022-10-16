package com.bahram.weather7.retrofit

import com.bahram.weather7.model.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    //    https://api.openweathermap.org/data/2.5/forecast?q=London&appid=9df97601ed495d20b3c3bb2b45b6c646
    @GET("forecast")
    fun getCityWeatherData(
        @Query("q") cityName: String,
        @Query("appid") api_key: String,
        @Query("units") units: String,
    ): Call<WeatherResponse>
}
