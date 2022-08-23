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
import com.bahram.weather7.model.*

class BriefAdapter(var context: Context, var brieflist: ArrayList<Final>?) : RecyclerView.Adapter<BriefAdapter.ViewHolderOne>() {

    override fun getItemCount(): Int {
        return brieflist?.size ?: 0
    }


    class ViewHolderOne(viewOne: View) : RecyclerView.ViewHolder(viewOne) {
        val llSelected: LinearLayout = viewOne.findViewById(R.id.linear_layout_brief)
        val tvCityNameB: TextView = viewOne.findViewById(R.id.text_view_city_brief)
        val tvDescriptionB: TextView = viewOne.findViewById(R.id.text_view_description_brief)
        val tvTempB: TextView = viewOne.findViewById(R.id.tv_temp_b)
        val tvTempMaxB: TextView = viewOne.findViewById(R.id.tv_temp_max_b)
        val tvTempMinB: TextView = viewOne.findViewById(R.id.tv_temp)

    }

    override fun getItemViewType(position: Int): Int {
//        val item = brieflist?.get(position)
//        return item?.items?.getOrNull(0)?.type?.id ?: 1
        return 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderOne {


        val view1 =
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_brief, parent, false)
        return ViewHolderOne(view1)
    }


//            ViewType.ONE.id -> {
//                val view1 =
//                    LayoutInflater.from(parent.context).inflate(R.layout.item_view_brief, parent, false)
//                ViewHolderOne(view1)
//            }
//
//            else
//            -> {
//                val view3 =
//                    LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_days, parent, false)
//                ViewHolderThree(view3)
//            }


    @SuppressLint("RestrictedApi")
    override fun onBindViewHolder(holder: ViewHolderOne, position: Int) {
        val item = brieflist?.get(position)
        val itemViewType = item?.items?.getOrNull(0)?.data

                val viewHolder1 = holder as ViewHolderOne
                val first = item?.items?.getOrNull(0)?.data as? Header
                viewHolder1.tvCityNameB.text = first?.cityName.toString() + ", " + first?.country.toString()
                viewHolder1.tvTempB.text = first?.currentTemp
                viewHolder1.tvDescriptionB.text = first?.description
                viewHolder1.tvTempMaxB.text = "H:" + first?.tempMax
                viewHolder1.tvTempMinB.text = "L:" + first?.tempMin

//                viewHolder1.tvAdd.setOnClickListener {
//                    var intent: Intent = Intent(context, MainActivity::class.java)
//                    intent.putExtra("For MainActivity: cityNameSelected", first?.cityName.toString())
//                    viewHolder1.tvAdd.context.startActivity(intent)
//                }

    }


}


