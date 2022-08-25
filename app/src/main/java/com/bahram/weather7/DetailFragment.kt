package com.bahram.weather7

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bahram.weather7.adapter.DetailFragmentAdapter
import com.bahram.weather7.model.Final

const val ARG_OBJECT = "object"

class DetailFragment(var aFinal: ArrayList<Final>) : Fragment() {

    lateinit var sharedPreferences: SharedPreferences

    lateinit var rvFragment: RecyclerView
    lateinit var fragmentAdapter: DetailFragmentAdapter

//    var final: ArrayList<Final?>? = null
//    var id: Int? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val viewFragment = LayoutInflater.from(requireContext()).inflate(R.layout.detail_fragment, container, false)

        rvFragment = viewFragment.findViewById(R.id.recycler_view_detail)

        return viewFragment


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            val textView: TextView = view.findViewById(R.id.textView)
            textView.text = getInt(ARG_OBJECT).toString()


        }
//        sharedItemsCityForPreview = requireContext().getSharedItemsCityForPreview("shared preferences", MODE_PRIVATE)
//        var value2 = arguments?.getString("cityNameForViewPager")
//        if (value2 != null) {
//            getCityWeather(value2.toString())
//        }
//
//        loadData()
//        Log.i("finalll", "$final")

        fragmentAdapter = DetailFragmentAdapter(requireContext(), aFinal)
        rvFragment.adapter = fragmentAdapter
        rvFragment.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)


    }


}


