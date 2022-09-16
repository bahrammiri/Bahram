package com.bahram.weather7.preview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bahram.weather7.model.WeatherResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PreviewViewModel : ViewModel() {

    val response = MutableLiveData<WeatherResponse>()

    fun newResponse(cityResponse: WeatherResponse?) {
        response.value = cityResponse



    }


}
