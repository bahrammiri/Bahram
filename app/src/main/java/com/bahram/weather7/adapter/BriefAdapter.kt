package com.bahram.weather7.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bahram.weather7.R
import com.bahram.weather7.ViewPagerActivity
import com.bahram.weather7.ViewPagerActivity.Companion.KEY_CITY_ITEM_POSITION
import com.bahram.weather7.model.CityItems
import com.bahram.weather7.model.Header
import com.bahram.weather7.model.ViewType

class BriefAdapter(var context: Context, var citiesItems: ArrayList<CityItems>?) :
    RecyclerView.Adapter<BriefAdapter.ViewHolderOne>() {

    override fun getItemCount(): Int {
        return citiesItems?.size ?: 0
    }

    class ViewHolderOne(viewOne: View) : RecyclerView.ViewHolder(viewOne) {
        val llSelected: LinearLayout = viewOne.findViewById(R.id.linear_layout_brief)
        val textViewCityBrief: TextView = viewOne.findViewById(R.id.text_view_city_brief)
        val textViewDescriptionBrief: TextView = viewOne.findViewById(R.id.text_view_description_brief)
        val textViewTempBrief: TextView = viewOne.findViewById(R.id.text_view_temp_brief)
        val textViewTempMaxBrief: TextView = viewOne.findViewById(R.id.text_view_temp_max_brief)
        val textViewTempMinBrief: TextView = viewOne.findViewById(R.id.text_view_temp_min_brief)


        //
        fun bind(citiesItems: CityItems) {
            val cityItems = citiesItems?.cityItems?.getOrNull(0)

            if (cityItems?.type == ViewType.ONE) {
                val header = cityItems.item as Header
                textViewCityBrief.text = header.cityName
                textViewTempBrief.text = header.currentTemp
                textViewDescriptionBrief.text = header.description
                textViewTempMaxBrief.text = "H:" + header.tempMax
                textViewTempMinBrief.text = "L:" + header.tempMin
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderOne {
        val view1 =
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_brief, parent, false)
        return ViewHolderOne(view1)
    }

    @SuppressLint("RestrictedApi")
    override fun onBindViewHolder(holder: ViewHolderOne, position: Int) {
        val item = citiesItems?.get(position)
        if (item != null) {
            holder.bind(item)
        }

        holder.llSelected.setOnClickListener {
            val intent = Intent(context, ViewPagerActivity::class.java)
            intent.putExtra(KEY_CITY_ITEM_POSITION, position)
            holder.llSelected.context.startActivity(intent)

        }
    }


}


