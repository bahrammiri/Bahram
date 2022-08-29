package com.bahram.weather7

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bahram.weather7.adapter.BriefAdapter
import com.bahram.weather7.util.SharedPreferencesManager
import com.bahram.weather7.model.Final
import com.bahram.weather7.model.Item
import com.bahram.weather7.model.ViewType
import com.bahram.weather7.model.WeatherResponse
import com.bahram.weather7.retrofit.Constants
import com.bahram.weather7.retrofit.RetrofitService
import com.bahram.weather7.util.WeatherResponseConverter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var cityNameInputted: String
    lateinit var relativeLayout: RelativeLayout
    lateinit var editTextCityName: EditText
    lateinit var recyclerViewBrief: RecyclerView

    var selectedListFinal: ArrayList<Final>? = null
    lateinit var briefAdapter: BriefAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        relativeLayout = findViewById(R.id.relative_layout_main)
        editTextCityName = findViewById(R.id.edit_text_city_name)
        recyclerViewBrief = findViewById(R.id.recycler_view_selected)

        loadData()

        if (selectedListFinal != null) {
            recyclerViewBrief.visibility = View.VISIBLE
            briefAdapter = BriefAdapter(this, selectedListFinal)
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
                        sendResponseToPreviewFragment(responseBody)
                    }
                }

                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                }

            })
    }

    fun sendResponseToPreviewFragment(response: WeatherResponse?) {
        val bundle = Bundle()
        bundle.putParcelable("response", response)
        val previewFragment = PreviewFragment()
        previewFragment.arguments = bundle
        val fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, previewFragment)
        fragmentTransaction.commit();
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadData() {
        val sh = SharedPreferencesManager(this)
        val weatherResponses = sh.loadCities()

        selectedListFinal = arrayListOf()
        weatherResponses.forEach { response ->
            val cityItems = arrayListOf<Item>()
            val weatherResponseConverter = WeatherResponseConverter()
            cityItems.add(Item(ViewType.ONE,
                weatherResponseConverter.createHeaderList(response)))
            cityItems.add(Item(ViewType.TWO,
                weatherResponseConverter.createHoursList(response)))
            cityItems.add(Item(ViewType.THREE,
                weatherResponseConverter.createDaysList(response)))
            selectedListFinal?.add(Final(cityItems))
        }

    }

}



