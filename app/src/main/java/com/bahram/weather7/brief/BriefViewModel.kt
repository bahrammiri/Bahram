package com.bahram.weather7.brief

import android.content.Context
import androidx.core.content.ContentProviderCompat.requireContext
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
        val selectedCities = sh.sharedPreferences.all
        val cities = sh.loadCities()
//        if (selectedCities != null) {
//
//        }
        loadCitiesItems(cities)
    }

    fun loadCitiesItems(cities: List<SharedPreferencesManager.CityName>) {
        val citySize = cities.size
        val citiesItems = ArrayList<CityItems>()
        if (citySize < 1) {
            return
        }
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
                            if (citiesItems.size == citySize) {
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

