package com.bahram.weather7

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bahram.weather7.PreviewFragment.Companion.KEY_DATA
import com.bahram.weather7.adapter.BriefAdapter
import com.bahram.weather7.model.CityItem
import com.bahram.weather7.model.CityItems
import com.bahram.weather7.model.ViewType
import com.bahram.weather7.model.WeatherResponse
import com.bahram.weather7.retrofit.Constants
import com.bahram.weather7.retrofit.RetrofitService
import com.bahram.weather7.util.SharedPreferencesManager
import com.bahram.weather7.util.WeatherResponseItemMapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var cityNameInputted: String
    lateinit var recyclerViewBrief: RecyclerView

    var citiesItems: ArrayList<CityItems>? = null
    lateinit var briefAdapter: BriefAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editTextCityName: EditText = findViewById(R.id.edit_text_city_name)
        recyclerViewBrief = findViewById(R.id.recycler_view_brief)

        loadCitiesItems()

        if (citiesItems != null) {
            recyclerViewBrief.visibility = View.VISIBLE
            briefAdapter = BriefAdapter(this, citiesItems)
            recyclerViewBrief.adapter = briefAdapter
            recyclerViewBrief.layoutManager =
                LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        }

        editTextCityName.setOnEditorActionListener { v, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                cityNameInputted = editTextCityName.text.toString()
                getCityWeather(cityNameInputted)

                val view = this.currentFocus
                if (view != null) {
                    val imm: InputMethodManager =
                        getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                    editTextCityName.clearFocus()
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

//                        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadCitiesItems() {
        val sh = SharedPreferencesManager(this)
        val weatherResponses = sh.loadCitiesResponses()

        citiesItems = arrayListOf()

        weatherResponses.forEach { response ->
            val cityItems = arrayListOf<CityItem>()
            val weatherResponseItemMapper = WeatherResponseItemMapper()

            cityItems.add(CityItem(ViewType.ONE, weatherResponseItemMapper.createHeaderList(response)))
            cityItems.add(CityItem(ViewType.TWO, weatherResponseItemMapper.createHoursList(response)))
            cityItems.add(CityItem(ViewType.THREE, weatherResponseItemMapper.createDaysList(response)))

            citiesItems?.add(CityItems(cityItems))
        }
    }


}



