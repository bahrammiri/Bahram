package com.bahram.weather7.brief

import android.content.Context
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

class BriefViewModel : ViewModel() {

    var citiesItems = MutableLiveData<ArrayList<CityItems>>()
    val isLoading = MutableLiveData<Boolean>(false)
    val errorMessage = MutableLiveData<String>("")

    fun start(context: Context) {
        val sh = SharedPreferencesManager(context)
        val cities = sh.loadCities()
        loadCitiesItems(cities)
    }

    fun removeCityFromCitiesItems(position: Int, context: Context) {
        val cityItem = citiesItems.value?.get(position)
        val cityName = (cityItem?.cityItems?.getOrNull(0)?.item as Header).cityName.toString()

        val sh = SharedPreferencesManager(context)
        sh.removeCityName(cityName)

        citiesItems.value?.removeAt(position)
        citiesItems.value = citiesItems.value

    }


    fun loadCitiesItems(cities: List<SharedPreferencesManager.CityName>) {

        val citiesItems = ArrayList<CityItems>()
        if (cities.isEmpty() == false) {


            isLoading.value = true

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

                                citiesItems.sortBy {
                                    (it.cityItems.getOrNull(0)?.item as Header).cityName
                                }
                                if (citiesItems.size == cities.size) {
                                    isLoading.value = false
                                }




                                this@BriefViewModel.citiesItems.value = citiesItems
                            }
                        }

                        override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                            isLoading.value = false
                            errorMessage.value = t.message + "${it.cityNameSelected}"
                        }
                    })
            }


        }
    }


}

