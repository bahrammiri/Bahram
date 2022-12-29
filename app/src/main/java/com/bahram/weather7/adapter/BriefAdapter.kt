package com.bahram.weather7.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bahram.weather7.brief.BriefFragmentDirections
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

        holder.bindig.briefLayout.setOnClickListener(
            Navigation.createNavigateOnClickListener(BriefFragmentDirections.actionBriefFragmentToDetailFragment(position))

        )


    }


}


