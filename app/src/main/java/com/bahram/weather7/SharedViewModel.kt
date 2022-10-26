package com.bahram.weather7

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bahram.weather7.model.CityItems
import com.bahram.weather7.model.Header
import com.bahram.weather7.model.WeatherResponse
import com.bahram.weather7.retrofit.Constants
import com.bahram.weather7.retrofit.RetrofitService
import com.bahram.weather7.util.SharedPreferencesManager
import com.bahram.weather7.util.WeatherResponseItemMapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SharedViewModel : ViewModel() {

    var citiesItems = MutableLiveData<ArrayList<CityItems>>()

    fun loadCitiesItems(cities: List<SharedPreferencesManager.CityName>) {
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

//                            citiesItems.forEachIndexed { index, cityItems ->
//
//                                val citiesItemsType = citiesItems.getOrNull(index)?.cityItems?.getOrNull(0)
//
//                                if (citiesItemsType?.type == ViewType.ONE) {
//                                    val header = citiesItemsType.item as Header
//
//                                    var sorted = ArrayList<CityItems>()
//                                    citiesItems.forEach {
//                                        header.cityName
//                                    }
//
//
//                                }

//                            }

                            citiesItems.sortBy {
                                (it.cityItems.getOrNull(0)?.item as Header).cityName
                            }

                            this@SharedViewModel.citiesItems.value = citiesItems
                        }
                    }

                    override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    }
                })
        }


    }


}
