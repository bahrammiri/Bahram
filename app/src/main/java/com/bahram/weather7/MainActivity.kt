package com.bahram.weather7

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bahram.weather7.adapter.BriefAdapter
import com.bahram.weather7.model.*
import com.bahram.weather7.retrofit.Constants
import com.bahram.weather7.retrofit.RetrofitService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var sharedResponse: SharedPreferences
    lateinit var sharedFinal: SharedPreferences

    lateinit var cityNameInputted: String
    lateinit var cities: List<String>

    lateinit var frameLayout: FrameLayout
    lateinit var relativeLayout: RelativeLayout
    lateinit var editTextCityName: EditText
    lateinit var recyclerViewBrief: RecyclerView

    var itemsCityForPreview = arrayListOf<Item>()
    var itemsCity = arrayListOf<Item>()
    var selectedListFinal: ArrayList<Final>? = null

    lateinit var briefAdapter: BriefAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        frameLayout = findViewById(R.id.frame_layout)
        relativeLayout = findViewById(R.id.relative_layout_main)
        editTextCityName = findViewById(R.id.edit_text_city_name)
        recyclerViewBrief = findViewById(R.id.recycler_view_selected)

        sharedResponse = applicationContext.getSharedPreferences("sharedResponse", MODE_PRIVATE)
        sharedFinal = applicationContext.getSharedPreferences("sharedFinal", MODE_PRIVATE)

        var intentCityNameSelected = intent.getStringExtra("For MainActivity: cityNameSelected")
        Log.i("intentCityNameSelected", "$intentCityNameSelected")

        cities = sharedResponse.all.map { it.key }
        Log.i("cities", "$cities")

        if (cities.isNotEmpty()) {
            selectedListFinal = getArrayList2("All cities ArrayList")

            hhh()
        }

        if (selectedListFinal != null) {
            recyclerViewBrief.visibility = View.VISIBLE
            briefAdapter = BriefAdapter(this, selectedListFinal)
            recyclerViewBrief.adapter = briefAdapter
            recyclerViewBrief.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        }

        editTextCityName.setOnEditorActionListener { v, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                cityNameInputted = editTextCityName.text.toString()


                getCityWeather(cityNameInputted)

                val view = this.currentFocus
                if (view != null) {
                    val imm: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                    editTextCityName.clearFocus()

                }

                true
            } else false
        }


    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun hhh(): ArrayList<Final>? {
        cities.forEach {
//            selectedListFinal = getArrayList2("All cities ArrayList")
            var response3 = getCityResponse(it)

            itemsCity.add(Item(ViewType.ONE, createHeaderList(response3)))
            itemsCity.add(Item(ViewType.TWO, createHoursList(response3)))
            itemsCity.add(Item(ViewType.THREE, createDaysList(response3)))

            selectedListFinal?.add(Final(itemsCity))
            saveArrayList2("All cities ArrayList", selectedListFinal)
            itemsCity.clear()
        }

        getArrayList2("All cities ArrayList")
        Log.i("selectedListFinal", "$selectedListFinal")
        return selectedListFinal
    }

    private fun getCityWeather(city: String) {
        RetrofitService.getInstance().getCityWeatherData(city, api_key = Constants.API_KEY, units = Constants.UNITS).enqueue(object :
            Callback<WeatherResponse> {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {

                val responseBody = response.body()
                if (responseBody != null) {
                    saveCityResponse(cityNameInputted, responseBody)
                    var aaa = getCityResponse(cityNameInputted)
                    createSelectedList(aaa)

                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
            }

        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createSelectedList(response: WeatherResponse?) {
        itemsCityForPreview.add(Item(ViewType.ONE, createHeaderList(response)))
        itemsCityForPreview.add(Item(ViewType.TWO, createHoursList(response)))
        itemsCityForPreview.add(Item(ViewType.THREE, createDaysList(response)))

//        selectedListFinal = getArrayList2("ttt")
//        selectedListFinal?.add(Final(itemsCityForPreview))
//
//        saveArrayList2("ttt", selectedListFinal)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.hide();

        relativeLayout.visibility = View.INVISIBLE
        frameLayout.visibility = View.VISIBLE

        val bundle = Bundle()
        bundle.putParcelableArrayList("For PreviewFragment", itemsCityForPreview)

        val fragment = PreviewFragment()
        fragment.arguments = bundle
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment, fragment.toString())
        fragmentTransaction.commit()
    }


    fun saveCityResponse(city: String, user: WeatherResponse) {
        sharedResponse.edit().putString(city, Gson().toJson(user)).apply()
    }

    fun getCityResponse(city: String): WeatherResponse? {
        val data = sharedResponse.getString(city, null)
        if (data == null) {
            return null
        }
        return Gson().fromJson(data, WeatherResponse::class.java)
    }

//    fun saveArrayList(cityInputted: String, list: ArrayList<Item>?) {
//        val editor: SharedPreferences.Editor = sharedItemsCityForPreview.edit()
//        val json: String = Gson().toJson(list)
//        editor.putString(cityInputted, json)
//        editor.apply()
//
//
//        Toast.makeText(this, "$cityInputted Saved", Toast.LENGTH_LONG)
//            .show()
//    }
//    fun getArrayList(cityInputted: String): ArrayList<Item>? {
//        val json = sharedItemsCityForPreview.getString(cityInputted, null)
//        if (json == "") return null
//        val type = object : TypeToken<ArrayList<Item>?>() {}.type
//        itemsCityForPreview = Gson().fromJson<ArrayList<Item>?>(json, type)
//        if (itemsCityForPreview == null) {
//            itemsCityForPreview = ArrayList()
//        }
//        return itemsCityForPreview
//    }


    fun saveArrayList2(key: String, list: ArrayList<Final>?) {
        val editor: SharedPreferences.Editor = sharedFinal.edit()
        val json: String = Gson().toJson(list)
        editor.putString(key, json)
        editor.apply()

    }

    fun getArrayList2(key: String): ArrayList<Final>? {
        val json = sharedFinal.getString(key, null)
        if (json == "") return null
        val type = object : TypeToken<ArrayList<Final>?>() {}.type
        selectedListFinal = Gson().fromJson<ArrayList<Final>?>(json, type)
        if (selectedListFinal == null) {
            selectedListFinal = ArrayList()
        }
        return selectedListFinal
    }


    fun createHeaderList(response: WeatherResponse?): Header {
        val tempCurrent = response?.list?.getOrNull(0)?.main?.temp ?: 0.0
        val tempMin1 = response?.list?.getOrNull(0)?.main?.tempMin ?: 0.0
        val tempMax1 = response?.list?.getOrNull(0)?.main?.tempMax ?: 0.0

        return Header(response?.city?.name ?: "*",
            response?.city?.country ?: "*",
            tempCurrent.toString().substringBefore(".") + "°",
            response?.list?.getOrNull(0)?.weather?.getOrNull(0)?.description?.capitalize(),
            tempMin1.toString().substringBefore(".") + "°",
            tempMax1.toString().substringBefore(".") + "°")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createHoursList(response: WeatherResponse?): ArrayList<Hours> {
        val hoursList = arrayListOf<Hours>()
        response?.list?.forEach {
            val hour2 = Util.timeStampToLocalHour(it.date ?: 0)
            val iconCode = it.weather?.getOrNull(0)?.icon
            val url = "https://openweathermap.org/img/wn/$iconCode@2x.png"
            val temp2 = it.main?.temp ?: 0.0
            hoursList.add(Hours(hour2, url, temp2.toString().substringBefore(".") + "°"))
        }
        return hoursList
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createDaysList(response: WeatherResponse?): ArrayList<Days> {
        val daysList = arrayListOf<Days>()
        response?.list?.forEachIndexed { index, weatherList ->

            if ((index == 2) or (index == 9) or (index == 17) or (index == 25) or (index == 33)) {
                val dayName = Util.timeStampToLocalDay(weatherList.date ?: 0).substring(0, 3).capitalize()
                val iconCode = weatherList.weather?.getOrNull(0)?.icon
                val url = "https://openweathermap.org/img/wn/$iconCode@2x.png"

                val tempMin3 = weatherList.main?.tempMin ?: 0.0

                val tempMax3 = weatherList.main?.tempMax ?: 0.0
                daysList.add(Days(dayName, url, tempMin3.toString().substringBefore(".") + "°", tempMax3.toString().substringBefore(".") + "°"))
            }
        }
        return daysList
    }


}



