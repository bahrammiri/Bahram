package com.bahram.weather7

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bahram.weather7.PreviewFragment.Companion.KEY_DATA
import com.bahram.weather7.adapter.BriefAdapter
import com.bahram.weather7.databinding.ActivityMainBinding
import com.bahram.weather7.model.CityItems
import com.bahram.weather7.model.WeatherResponse
import com.bahram.weather7.retrofit.Constants
import com.bahram.weather7.retrofit.RetrofitService
import com.bahram.weather7.util.WeatherResponseItemMapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var citiesItems: ArrayList<CityItems>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        citiesItems = WeatherResponseItemMapper.loadCitiesItems(this)

        if (citiesItems != null) {
            binding.recyclerViewBrief.visibility = View.VISIBLE
            binding.recyclerViewBrief.adapter = BriefAdapter(this, citiesItems)
            binding.recyclerViewBrief.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        }

        binding.editTextCityName.setOnEditorActionListener { v, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val cityNameInputted = binding.editTextCityName.text.toString()
                getCityWeather(cityNameInputted)

                val view = this.currentFocus
                if (view != null) {
                    val imm: InputMethodManager =
                        getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                    binding.editTextCityName.clearFocus()
                }
                true
            } else false
        }

    }

    private fun getCityWeather(city: String) {
        RetrofitService.getInstance()
            .getCityWeatherData(city, api_key = Constants.API_KEY, units = Constants.UNITS)
            .enqueue(object :
                Callback<WeatherResponse> {
                override fun onResponse(
                    call: Call<WeatherResponse>,
                    response: Response<WeatherResponse>,
                ) {

                    val responseBody = response.body()
                    if (responseBody != null) {
                        supportActionBar?.setDisplayShowTitleEnabled(false)
                        supportActionBar?.hide();
                        actionBar?.hide()

                        sendCityResponseToPreviewFragment(responseBody)
                    }
                }

                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                }

            })
    }

    fun sendCityResponseToPreviewFragment(response: WeatherResponse?) {
        val bundle = Bundle()
        bundle.putParcelable(KEY_DATA, response)
        val previewFragment = PreviewFragment()
        previewFragment.arguments = bundle
        val fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, previewFragment)
        fragmentTransaction.commit();
    }
}



