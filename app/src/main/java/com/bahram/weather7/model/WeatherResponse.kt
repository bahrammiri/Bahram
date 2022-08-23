package com.bahram.weather7.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("list")
    var list: ArrayList<WeatherList>? = arrayListOf(),
    @SerializedName("city")
    var city: City?,
)

data class WeatherList(
    @SerializedName("dt") var date: Long? = null,
    @SerializedName("main") var main: Main? = Main(),
    @SerializedName("weather") var weather: ArrayList<Weather>?,
    @SerializedName("wind") var wind: Wind? = Wind(),
)

data class Weather(
    @SerializedName("id")
    val id: Long?,
    @SerializedName("main")
    val main: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("icon")
    val icon: String?,
)

data class Main(
    @SerializedName("temp") var temp: Double? = null,
    @SerializedName("feels_like") var feelsLike: Double? = null,
    @SerializedName("temp_min") var tempMin: Double? = null,
    @SerializedName("temp_max") var tempMax: Double? = null,
    @SerializedName("pressure") var pressure: Int? = null,
    @SerializedName("sea_level") var seaLevel: Int? = null,
    @SerializedName("grnd_level") var grndLevel: Int? = null,
    @SerializedName("humidity") var humidity: Int? = null,
    @SerializedName("temp_kf") var tempKf: Double? = null,
)

data class Wind(
    @SerializedName("speed") var speed: Double? = null,
    @SerializedName("deg") var deg: Int? = null,
    @SerializedName("gust") var gust: Double? = null,
)

data class City(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("coord") var coord: Coord? = null,
    @SerializedName("country") var country: String? = null,
    @SerializedName("population") var population: Int? = null,
    @SerializedName("timezone") var timezone: Int? = null,
    @SerializedName("sunrise") var sunrise: Int? = null,
    @SerializedName("sunset") var sunset: Int? = null,
)

data class Coord(
    @SerializedName("lat") var latitude: Double? = null,
    @SerializedName("lon") var longitude: Double? = null,
)

