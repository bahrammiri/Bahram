package com.bahram.weather7

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bahram.weather7.adapter.DetailViewPagerAdapter
import com.bahram.weather7.util.SharedPreferencesManager
import com.bahram.weather7.model.Final
import com.bahram.weather7.model.Item
import com.bahram.weather7.model.ViewType
import com.bahram.weather7.util.WeatherResponseConverter

class ViewPagerActivity : AppCompatActivity() {
    var selectedListFinal: ArrayList<Final>? = null
    private lateinit var viewPager2: ViewPager2

    companion object{

        val KEY_CITY_ITEM_POSITION = "KEY_CITY_ITEM_POSITION"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)

        loadData()



        val position = intent.getIntExtra(KEY_CITY_ITEM_POSITION,0)!!

        viewPager2 = findViewById(R.id.detail_view_pager)
        val viewPagerAdapter = DetailViewPagerAdapter(this, selectedListFinal)
        viewPager2.adapter = viewPagerAdapter
        viewPager2.setCurrentItem(position, false)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadData() {
        val sh = SharedPreferencesManager(this)
        val weatherResponses = sh.loadCities()

        selectedListFinal = arrayListOf()
        weatherResponses.forEach { response ->
            val itemsCityForPreview = arrayListOf<Item>()
            val weatherResponseConverter = WeatherResponseConverter()
            itemsCityForPreview.add(Item(ViewType.ONE,
                weatherResponseConverter.createHeaderList(response)))
            itemsCityForPreview.add(Item(ViewType.TWO,
                weatherResponseConverter.createHoursList(response)))
            itemsCityForPreview.add(Item(ViewType.THREE,
                weatherResponseConverter.createDaysList(response)))
            selectedListFinal?.add(Final(itemsCityForPreview))
        }
    }

}