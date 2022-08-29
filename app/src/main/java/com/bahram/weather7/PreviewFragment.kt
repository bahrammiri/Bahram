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
import com.bahram.weather7.model.Item
import com.bahram.weather7.model.ViewType
import com.bahram.weather7.model.WeatherResponse
import com.bahram.weather7.util.WeatherResponseConverter

class PreviewFragment : Fragment() {

    lateinit var recyclerViewPreview: RecyclerView
    lateinit var previewFragmentAdapter: PreviewFragmentAdapter

    companion object {
        val KEY_DATA = "KEY_DATA"
        val KEY_STATE = "KEY_STATE"
        val VALUE_STATE_PREVIEW_MODE = "VALUE_STATE_PREVIEW_MODE"
        val VALUE_STATE_INSERT_MODE = "VALUE_STATE_INSERT_MODE"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val view = LayoutInflater.from(requireContext())
            .inflate(R.layout.preview_fragment, container, false)
        recyclerViewPreview = view.findViewById(R.id.recycler_view_preview)
        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val response = arguments?.getParcelable<WeatherResponse>(KEY_DATA)
        val state = arguments?.getString(KEY_STATE)
        if (state == VALUE_STATE_PREVIEW_MODE) {

        } else if (state == VALUE_STATE_INSERT_MODE) {


        }

        val weatherResponseConverter = WeatherResponseConverter()

        var itemsCityForPreview = arrayListOf<Item>()
        itemsCityForPreview.add(Item(ViewType.ONE,
            weatherResponseConverter.createHeaderList(response)))
        itemsCityForPreview.add(Item(ViewType.TWO,
            weatherResponseConverter.createHoursList(response)))
        itemsCityForPreview.add(Item(ViewType.THREE,
            weatherResponseConverter.createDaysList(response)))

        previewFragmentAdapter =
            PreviewFragmentAdapter(response!!, requireContext(), itemsCityForPreview)
        recyclerViewPreview.adapter = previewFragmentAdapter
        recyclerViewPreview.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }
}

