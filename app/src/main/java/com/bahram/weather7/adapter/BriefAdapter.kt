package com.bahram.weather7.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bahram.weather7.ViewPagerActivity
import com.bahram.weather7.ViewPagerActivity.Companion.KEY_CITY_ITEM_POSITION
import com.bahram.weather7.databinding.ItemViewBriefBinding
import com.bahram.weather7.model.CityItems
import com.bahram.weather7.model.Header
import com.bahram.weather7.model.ViewType

class BriefAdapter(var context: Context, var citiesItems: ArrayList<CityItems>?) :
    RecyclerView.Adapter<BriefAdapter.ViewHolderOne>() {

    override fun getItemCount(): Int {
        return citiesItems?.size ?: 0
    }

    class ViewHolderOne(val bindig: ItemViewBriefBinding) : RecyclerView.ViewHolder(bindig.root) {
//        val briefLayout: ConstraintLayout = viewOne.findViewById(R.id.brief_layout)
//        private val textViewCityBrief: TextView = viewOne.findViewById(R.id.text_view_city_brief)
//        private val textViewDescriptionBrief: TextView = viewOne.findViewById(R.id.text_view_description_brief)
//        private val textViewTempBrief: TextView = viewOne.findViewById(R.id.text_view_temp_brief)
//        private val textViewTempMaxBrief: TextView = viewOne.findViewById(R.id.text_view_temp_max_brief)
//        private val textViewTempMinBrief: TextView = viewOne.findViewById(R.id.text_view_temp_min_brief)

        fun bind(citiesItems: CityItems) {
            val cityItems = citiesItems.cityItems.getOrNull(0)

            if (cityItems?.type == ViewType.ONE) {
                val header = cityItems.item as Header
                bindig.textViewCityBrief.text = header.cityName
                bindig.textViewTempBrief.text = header.currentTemp
                bindig.textViewDescriptionBrief.text = header.description
                bindig.textViewTempMaxBrief.text = "H:" + header.tempMax
                bindig.textViewTempMinBrief.text = "L:" + header.tempMin
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderOne {
//        val view1 =
//            LayoutInflater.from(parent.context).inflate(R.layout.item_view_brief, parent, false)
//        return ViewHolderOne(view1)

        val binding = ItemViewBriefBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderOne(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderOne, position: Int) {
        val item = citiesItems?.get(position)
        if (item != null) {
            holder.bind(item)
        }

        holder.bindig.briefLayout.setOnClickListener {
            val intent = Intent(context, ViewPagerActivity::class.java)
            intent.putExtra(KEY_CITY_ITEM_POSITION, position)
            holder.bindig.briefLayout.context.startActivity(intent)

        }
    }


}


