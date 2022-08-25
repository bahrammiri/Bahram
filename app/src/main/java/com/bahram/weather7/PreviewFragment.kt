package com.bahram.weather7

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bahram.weather7.adapter.PreviewFragmentAdapter
import com.bahram.weather7.model.Item
import com.bahram.weather7.model.ViewType
import com.bahram.weather7.model.WeatherResponse


class PreviewFragment : Fragment() {

    lateinit var recyclerViewPreview: RecyclerView
    lateinit var previewFragmentAdapter: PreviewFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val viewPF = LayoutInflater.from(requireContext())
            .inflate(R.layout.preview_fragment, container, false)
        recyclerViewPreview = viewPF.findViewById(R.id.recycler_view_preview)
        return viewPF
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val response = arguments?.getParcelable<WeatherResponse>("response")
        Log.i("response", response.toString())

        val weatherResponseConvertorToC = WeatherResponseConvertorToC()

        var itemsCityForPreview = arrayListOf<Item>()

        itemsCityForPreview.add(Item(ViewType.ONE,
            weatherResponseConvertorToC.createHeaderList(response)))
        itemsCityForPreview.add(Item(ViewType.TWO,
            weatherResponseConvertorToC.createHoursList(response)))
        itemsCityForPreview.add(Item(ViewType.THREE,
            weatherResponseConvertorToC.createDaysList(response)))

        previewFragmentAdapter =
            PreviewFragmentAdapter(response!!,requireContext(), itemsCityForPreview)
        recyclerViewPreview.adapter = previewFragmentAdapter
        recyclerViewPreview.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

    }


}

