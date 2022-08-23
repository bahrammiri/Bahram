package com.bahram.weather7.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Final(var items: @RawValue ArrayList<Item>) : Parcelable

@Parcelize
data class Item(val type: ViewType, val data: @RawValue Any) : Parcelable

@Parcelize
enum class ViewType(val id: Int) : Parcelable {
    ONE(1),
    TWO(2),
    THREE(3),
}

@Parcelize
data class Header(
    val cityName: String?,
    val country: String?,
    val currentTemp: String?,
    val description: String?,
    val tempMin: String?,
    val tempMax: String?,
) : Parcelable

@Parcelize
data class Hours(
    val hour: String?,
    val icon: String?,
    val temp: String?,
) : Parcelable

@Parcelize
data class Days(
    val day: String?,
    val iconDay: String?,
    val tempMinDay: String?,
    val tempMaxDay: String?,
) : Parcelable

@Parcelize
data class ViewFour(
    val speed: Double? = null,
    val deg: Int? = null,
    val gust: Double? = null,
) : Parcelable











