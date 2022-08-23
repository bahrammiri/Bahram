package com.bahram.weather7

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bahram.weather7.adapter.BriefAdapter
import com.bahram.weather7.retrofit.Constants
import com.bahram.weather7.retrofit.RetrofitService
import com.bahram.weather7.model.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var sharedTemporaryCityName: SharedPreferences
    lateinit var sharedArrayListSelected: SharedPreferences

    lateinit var frameLayout: FrameLayout
    lateinit var relativeLayout: RelativeLayout
    lateinit var editTextCityName: EditText
    lateinit var recyclerViewBrief: RecyclerView

    var selectedList: ArrayList<Final>? = ArrayList<Final>()
    var selectedList2: ArrayList<Final>? = ArrayList<Final>()
    var selectedListFromShared: ArrayList<Final>? = ArrayList<Final>()

    lateinit var briefAdapter: BriefAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedTemporaryCityName = getSharedPreferences("Shared City Name", MODE_PRIVATE)
        var editorCity = sharedTemporaryCityName.edit()

        sharedArrayListSelected = getSharedPreferences("Shared ArrayList", MODE_PRIVATE)

        frameLayout = findViewById(R.id.frame_layout)
        relativeLayout = findViewById(R.id.relative_layout_main)
        editTextCityName = findViewById(R.id.edit_text_city_name)
        recyclerViewBrief = findViewById(R.id.recycler_view_selected)

        var getCityNameInputted = sharedTemporaryCityName.getString("cityNameInputted", null)
        var intentCityNameSelected = intent.getStringExtra("For MainActivity: cityNameSelected")

//        selectedListFromShared = getArrayList("selectedList")

//        Log.i("selectedListFromShared ->", "$selectedListFromShared")

//        val nnn = (intentCityNameSelected != getCityNameInputted)
//
//        Log.i("bb", "$nnn")

//        if (intentCityNameSelected != getCityNameInputted) {
//            editorCity.clear()
//            selectedListFromShared?.removeLast()
//            saveArrayList("selectedList",selectedListFromShared)
//        } else {
//            editorCity.clear()
//        }
        selectedList = getArrayList("selectedList")

//        Log.i("Log after the first get-> selectedList ->", "$selectedList")

        recyclerViewBrief.visibility = View.VISIBLE
        briefAdapter = BriefAdapter(this, selectedList2)
        recyclerViewBrief.adapter = briefAdapter
        recyclerViewBrief.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        editTextCityName.setOnEditorActionListener { v, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                val cityNameInputted = editTextCityName.text.toString()

                editorCity.putString("cityNameInputted", cityNameInputted)
                editorCity.apply()

                getCityWeather(editTextCityName.text.toString())

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

    private fun getCityWeather(city: String) {
        RetrofitService.getInstance().getCityWeatherData(city, api_key = Constants.API_KEY, units = Constants.UNITS).enqueue(object :
            Callback<WeatherResponse> {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {

                val responseBody = response.body()
                if (responseBody != null) {
                    createSelectedList(responseBody)

                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
            }

        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createSelectedList(response: WeatherResponse?) {

        var itemsCityForPreview = arrayListOf<Item>()
        itemsCityForPreview.add(Item(ViewType.ONE, createHeaderList(response)))
        itemsCityForPreview.add(Item(ViewType.TWO, createHoursList(response)))
        itemsCityForPreview.add(Item(ViewType.THREE, createDaysList(response)))

        selectedList?.add(Final(itemsCityForPreview))

        saveArrayList("selectedList", selectedList)
        selectedList2 = getArrayList("selectedList")
        Log.i("Log after the first get-> selectedList2 ->", "$selectedList2")

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


//    private val selectedItemListener = BriefAdapter.OnClickListener { itemsCity ->
//        val intent = Intent(this, ViewPagerActivity::class.java)
//        intent.putExtra("Intent to ViewPagerActivity", itemsCity)
//        startActivity(intent)
//    }


    fun saveArrayList(key: String, list: ArrayList<Final>?) {
        val editor: SharedPreferences.Editor = sharedArrayListSelected.edit()
        val json: String = Gson().toJson(list)
        editor.putString(key, json)
        editor.apply()
        Toast.makeText(this, "$key Saved", Toast.LENGTH_LONG)
            .show()
    }


    fun getArrayList(key: String): ArrayList<Final>? {
        val json = sharedArrayListSelected.getString(key, null)
        if (json == "") return null
        val type = object : TypeToken<ArrayList<Final>?>() {}.type
        selectedList = Gson().fromJson<ArrayList<Final>?>(json, type)
        if (selectedList == null) {
            selectedList = ArrayList()
        }
        return selectedList
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



