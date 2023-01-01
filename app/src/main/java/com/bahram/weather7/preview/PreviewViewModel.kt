package com.bahram.weather7.preview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bahram.weather7.model.CityItem
import com.bahram.weather7.model.WeatherResponse
import com.bahram.weather7.retrofit.Constants
import com.bahram.weather7.retrofit.RetrofitService
import com.bahram.weather7.util.WeatherResponseItemMapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PreviewViewModel : ViewModel() {

    var cityItems = MutableLiveData<ArrayList<CityItem>>()
    val errorMessage = MutableLiveData<String>("")


    fun loadCityItems(cityNameInputted: String) {
        RetrofitService.getInstance()
            .getCityWeatherData(cityNameInputted, api_key = Constants.API_KEY, units = Constants.UNITS)
            .enqueue(object :
                Callback<WeatherResponse> {
                override fun onResponse(
                    call: Call<WeatherResponse>,
                    response: Response<WeatherResponse>,
                ) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        cityItems.value = WeatherResponseItemMapper.loadCityItems(responseBody)
                    } else {
                        errorMessage.value = response.errorBody().toString()
                    }
                }

                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {


                }
            })
    }
}


