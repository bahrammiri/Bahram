package com.bahram.weather7.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bahram.weather7.R
import com.bahram.weather7.model.Final
import com.bahram.weather7.model.Header
import com.bahram.weather7.model.Item
import com.bahram.weather7.model.ViewType

class BriefAdapter(var context: Context, var brieflist: ArrayList<Final>?) :
    RecyclerView.Adapter<BriefAdapter.ViewHolderOne>() {

    override fun getItemCount(): Int {
        return brieflist?.size ?: 0
    }

    class ViewHolderOne(viewOne: View) : RecyclerView.ViewHolder(viewOne) {
        val llSelected: LinearLayout = viewOne.findViewById(R.id.linear_layout_brief)
        val tvCityNameB: TextView = viewOne.findViewById(R.id.text_view_city_brief)
        val tvDescriptionB: TextView = viewOne.findViewById(R.id.text_view_description_brief)
        val tvTempB: TextView = viewOne.findViewById(R.id.tv_temp_b)
        val tvTempMaxB: TextView = viewOne.findViewById(R.id.tv_temp_max_b)
//        val tvTempMinB: TextView = viewOne.findViewById(R.id.tv_temp)

    }

//    override fun getItemViewType(position: Int): Int {
////        val item = brieflist?.get(position)
////        return item?.items?.getOrNull(0)?.type?.id ?: 0
//        return 1
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderOne {
        val view1 =
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_brief, parent, false)
        return ViewHolderOne(view1)
    }


    @SuppressLint("RestrictedApi")
    override fun onBindViewHolder(holder: ViewHolderOne, position: Int) {
        val item = brieflist?.get(position)

        val detailsItem: Item? = item?.items?.getOrNull(0)

        if (detailsItem?.type == ViewType.ONE) {

            val headerData = detailsItem.data as Header

            holder.tvCityNameB.text = headerData.cityName
            holder.tvTempB.text = headerData.currentTemp
            holder.tvDescriptionB.text = headerData.description
            holder.tvTempMaxB.text = headerData.tempMax

        }

//        if (detailsItem == ViewType.ONE) {
//            val first = item?.items?.getOrNull(0)?.data as Header
//
//            holder.tvCityNameB.text = first?.cityName.toString() + ", " + first?.country.toString()
//            holder.tvTempB.text = first?.currentTemp
//            holder.tvDescriptionB.text = first?.description
//            holder.tvTempMaxB.text = "H:" + first?.tempMax
//        } else {
////            throw IllegalArgumentException
//        }
    }
}


