package com.bahram.weather7.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bahram.weather7.R
import com.bahram.weather7.adapter.ViewPagerAdapter
import com.bahram.weather7.brief.BriefFragment
import com.bahram.weather7.databinding.FragmentDetailBinding
import com.bahram.weather7.model.CityItems
import com.bahram.weather7.preview.PreviewFragment
import com.bahram.weather7.util.SharedPreferencesManager
import com.google.android.material.tabs.TabLayoutMediator

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var viewModel: DetailViewModel

    companion object {
        const val KEY_CITY_ITEM_POSITION = "KEY_CITY_ITEM_POSITION"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonBack.setOnClickListener {
            val briefFragment = BriefFragment()
            val transaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container_main, briefFragment)
            transaction.commit()
        }

        val position = requireArguments().getInt(KEY_CITY_ITEM_POSITION)

        viewModel = ViewModelProvider(requireActivity()).get(DetailViewModel::class.java)
        val sh = SharedPreferencesManager(requireContext())
        val cities = sh.loadCities()

        viewModel.citiesItems.observe(viewLifecycleOwner) {
            if(cities.size == viewModel.citiesItems.value?.size) {
                binding.viewPager2.setCurrentItem(position, false)
                val viewPagerAdapter = ViewPagerAdapter(requireContext(), viewModel.citiesItems.value)
                binding.viewPager2.adapter = viewPagerAdapter

//                viewPagerAdapter.notifyDataSetChanged()

                val tabLayoutMediator = TabLayoutMediator(binding.tabLayout, binding.viewPager2, true
                ) { tab, position -> }
                tabLayoutMediator.attach()
            }
        }

        viewModel.loadCitiesItems(cities)

    }

}


