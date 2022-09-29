package com.bahram.weather7.brief

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bahram.weather7.model.CityItems
import com.bahram.weather7.model.WeatherResponse
import com.bahram.weather7.retrofit.Constants
import com.bahram.weather7.retrofit.RetrofitService
import com.bahram.weather7.util.SharedPreferencesManager
import com.bahram.weather7.util.WeatherResponseItemMapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BriefViewModel : ViewModel() {

    var citiesItems = MutableLiveData<ArrayList<CityItems>>()

    fun loadCitiesItems(cities: ArrayList<SharedPreferencesManager.CityName>) {
        val citiesItems = ArrayList<CityItems>()
        cities.forEach {
            RetrofitService.getInstance()
                .getCityWeatherData(it.cityNameSelected, api_key = Constants.API_KEY, units = Constants.UNITS)
                .enqueue(object :
                    Callback<WeatherResponse> {
                    override fun onResponse(
                        call: Call<WeatherResponse>,
                        response: Response<WeatherResponse>,
                    ) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            citiesItems.add(CityItems(WeatherResponseItemMapper.loadCityItems(responseBody)))
                            this@BriefViewModel.citiesItems.value = citiesItems
                        }
                    }

                    override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    }
                })
        }


    }


}
