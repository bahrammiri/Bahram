package com.bahram.weather7

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bahram.weather7.adapter.PreviewFragmentAdapter
import com.bahram.weather7.model.CityItem
import com.bahram.weather7.model.ViewType
import com.bahram.weather7.model.WeatherResponse
import com.bahram.weather7.util.WeatherResponseItemMapper

class PreviewFragment : Fragment() {

    private lateinit var recyclerViewPreview: RecyclerView
    private lateinit var previewFragmentAdapter: PreviewFragmentAdapter

    companion object {
        const val KEY_DATA = "KEY_DATA"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.preview_fragment, container, false)
        recyclerViewPreview = view.findViewById(R.id.recycler_view_preview)
        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cityResponse = arguments?.getParcelable<WeatherResponse>(KEY_DATA)
        previewFragmentAdapter = PreviewFragmentAdapter(requireContext(), loadCityItemsForPreview(cityResponse), cityResponse)
        recyclerViewPreview.adapter = previewFragmentAdapter
        recyclerViewPreview.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadCityItemsForPreview(response: WeatherResponse?): ArrayList<CityItem> {
        val cityItems = arrayListOf<CityItem>()
        val weatherResponseItemMapper = WeatherResponseItemMapper()
        cityItems.add(CityItem(ViewType.ONE, weatherResponseItemMapper.createHeaderList(response)))
        cityItems.add(CityItem(ViewType.TWO, weatherResponseItemMapper.createHoursList(response)))
        cityItems.add(CityItem(ViewType.THREE, weatherResponseItemMapper.createDaysList(response)))

        return cityItems
    }

}

