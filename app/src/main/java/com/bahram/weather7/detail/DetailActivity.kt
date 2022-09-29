package com.bahram.weather7.detail

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bahram.weather7.adapter.ViewPagerAdapter
import com.bahram.weather7.databinding.ActivityDetailBinding
import com.bahram.weather7.main.MainActivity
import com.bahram.weather7.model.CityItems
import com.bahram.weather7.util.SharedPreferencesManager
import com.google.android.material.tabs.TabLayoutMediator


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    var citiesItems: ArrayList<CityItems>? = null

    companion object {
        const val KEY_CITY_ITEM_POSITION = "KEY_CITY_ITEM_POSITION"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_view_pager)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.hide();
        actionBar?.hide()

//        cityItems = WeatherResponseItemMapper.loadCitiesItems(this)
//        cityItems = WeatherResponseItemMapper.loadCitiesItems2(this)
        val position = intent.getIntExtra(KEY_CITY_ITEM_POSITION, 0)

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        val sh = SharedPreferencesManager(this)
        val cities = sh.loadCities()

        viewModel.citiesItems.observe(this, Observer {
            binding.viewPager2.setCurrentItem(position, false)
            val viewPagerAdapter = ViewPagerAdapter(this, viewModel.citiesItems.value)
            binding.viewPager2.adapter = viewPagerAdapter

            viewPagerAdapter.notifyDataSetChanged()
            val tabLayoutMediator = TabLayoutMediator(binding.tabLayout, binding.viewPager2, true
            ) { tab, position -> }
            tabLayoutMediator.attach()
        })

        viewModel.loadCitiesItems(cities)

//        val viewPager2 = findViewById<ViewPager2>(R.id.view_pager2)
//        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
//        val buttonBack = findViewById<Button>(R.id.button_back)

        binding.buttonBack.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

//        val viewPagerAdapter = ViewPagerAdapter(this, cityItems)
//        binding.viewPager2.adapter = viewPagerAdapter
//        binding.viewPager2.setCurrentItem(position, false)
//
//        val tabLayoutMediator = TabLayoutMediator(binding.tabLayout, binding.viewPager2, true
//        ) { tab, position -> }
//        tabLayoutMediator.attach()

    }

//    private fun loadCitiesItems() {
//        val sh = SharedPreferencesManager(this)
//        val weatherResponses = sh.loadCitiesResponses()
//        cityItems = arrayListOf()
//        weatherResponses.forEach { response ->
//            val cityItems = arrayListOf<CityItem>()
//            val weatherResponseItemMapper = WeatherResponseItemMapper()
//            cityItems.add(CityItem(ViewType.ONE, weatherResponseItemMapper.createHeaderList(response)))
//            cityItems.add(CityItem(ViewType.TWO, weatherResponseItemMapper.createHoursList(response)))
//            cityItems.add(CityItem(ViewType.THREE, weatherResponseItemMapper.createDaysList(response)))
//            cityItems?.add(CityItems(cityItems))
//        }
//    }

}