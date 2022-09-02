package com.bahram.weather7

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bahram.weather7.adapter.ViewPagerAdapter
import com.bahram.weather7.model.CityItem
import com.bahram.weather7.model.CityItems
import com.bahram.weather7.model.ViewType
import com.bahram.weather7.util.SharedPreferencesManager
import com.bahram.weather7.util.WeatherResponseItemCreator
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class ViewPagerActivity : AppCompatActivity() {

    var citiesItems: ArrayList<CityItems>? = null

    companion object {
        const val KEY_CITY_ITEM_POSITION = "KEY_CITY_ITEM_POSITION"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.hide();
        actionBar?.hide()

        loadCitiesItems()


        val position = intent.getIntExtra(KEY_CITY_ITEM_POSITION, 0)

        val viewPager2 = findViewById<ViewPager2>(R.id.view_pager)
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        val myToolbar = findViewById<Toolbar>(R.id.my_toolbar)

        myToolbar.setOnClickListener{
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val viewPagerAdapter = ViewPagerAdapter(this, citiesItems)
        viewPager2.adapter = viewPagerAdapter
        viewPager2.setCurrentItem(position, false)

        val tabLayoutMediator = TabLayoutMediator(tabLayout, viewPager2, true
        ) { tab, position -> }
        tabLayoutMediator.attach()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadCitiesItems() {
        val sh = SharedPreferencesManager(this)
        val weatherResponses = sh.loadCitiesResponses()
        citiesItems = arrayListOf()
        weatherResponses.forEach { response ->
            val cityItems = arrayListOf<CityItem>()
            val weatherResponseItemCreator = WeatherResponseItemCreator()
            cityItems.add(CityItem(ViewType.ONE, weatherResponseItemCreator.createHeaderList(response)))
            cityItems.add(CityItem(ViewType.TWO, weatherResponseItemCreator.createHoursList(response)))
            cityItems.add(CityItem(ViewType.THREE, weatherResponseItemCreator.createDaysList(response)))
            citiesItems?.add(CityItems(cityItems))
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.menu_main,menu)
//        return super.onCreateOptionsMenu(menu)
//    }


}