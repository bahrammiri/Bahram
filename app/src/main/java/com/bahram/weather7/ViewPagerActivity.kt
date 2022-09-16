package com.bahram.weather7

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bahram.weather7.adapter.ViewPagerAdapter
import com.bahram.weather7.databinding.ActivityViewPagerBinding
import com.bahram.weather7.main.MainActivity
import com.bahram.weather7.model.CityItems
import com.bahram.weather7.util.WeatherResponseItemMapper
import com.google.android.material.tabs.TabLayoutMediator


class ViewPagerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewPagerBinding

    var citiesItems: ArrayList<CityItems>? = null

    companion object {
        const val KEY_CITY_ITEM_POSITION = "KEY_CITY_ITEM_POSITION"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_view_pager)
        binding = ActivityViewPagerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.hide();
        actionBar?.hide()

        citiesItems = WeatherResponseItemMapper.loadCitiesItems(this)

        val position = intent.getIntExtra(KEY_CITY_ITEM_POSITION, 0)

//        val viewPager2 = findViewById<ViewPager2>(R.id.view_pager2)
//        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
//        val buttonBack = findViewById<Button>(R.id.button_back)

        binding.buttonBack.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val viewPagerAdapter = ViewPagerAdapter(this, citiesItems)
        binding.viewPager2.adapter = viewPagerAdapter
        binding.viewPager2.setCurrentItem(position, false)

        val tabLayoutMediator = TabLayoutMediator(binding.tabLayout, binding.viewPager2, true
        ) { tab, position -> }
        tabLayoutMediator.attach()

    }

//    private fun loadCitiesItems() {
//        val sh = SharedPreferencesManager(this)
//        val weatherResponses = sh.loadCitiesResponses()
//        citiesItems = arrayListOf()
//        weatherResponses.forEach { response ->
//            val cityItems = arrayListOf<CityItem>()
//            val weatherResponseItemMapper = WeatherResponseItemMapper()
//            cityItems.add(CityItem(ViewType.ONE, weatherResponseItemMapper.createHeaderList(response)))
//            cityItems.add(CityItem(ViewType.TWO, weatherResponseItemMapper.createHoursList(response)))
//            cityItems.add(CityItem(ViewType.THREE, weatherResponseItemMapper.createDaysList(response)))
//            citiesItems?.add(CityItems(cityItems))
//        }
//    }

}