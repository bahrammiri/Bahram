package com.bahram.weather7.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bahram.weather7.R
import com.bahram.weather7.adapter.detail.DaysAdapter
import com.bahram.weather7.adapter.detail.HoursAdapter
import com.bahram.weather7.model.*

class MainAdapter(var context: Context,
    var listPreview: ArrayList<Item>?,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var hoursAdapter: HoursAdapter
    lateinit var daysAdapter: DaysAdapter

    override fun getItemCount(): Int {
        return listPreview?.size ?: 0
    }

    class ViewHolderOne(viewOne: View) : RecyclerView.ViewHolder(viewOne) {
        val tvCityCountry: TextView = viewOne.findViewById<TextView>(R.id.text_view_city_country)
        val tvTempCurrent: TextView = viewOne.findViewById<TextView>(R.id.text_view_temp_current)
        val tvDescription: TextView = viewOne.findViewById<TextView>(R.id.text_view_description)
        val tvTempMax: TextView = viewOne.findViewById<TextView>(R.id.text_view_temp_max)
        val tvTempMin: TextView = viewOne.findViewById<TextView>(R.id.text_view_temp_min)
    }

    class ViewHolderTwo(viewTwo: View) : RecyclerView.ViewHolder(viewTwo) {
        val rvHours = viewTwo.findViewById<RecyclerView>(R.id.recycler_view_hours)
    }

    class ViewHolderThree(viewThree: View) : RecyclerView.ViewHolder(viewThree) {
        val rvDays = viewThree.findViewById<RecyclerView>(R.id.recycler_view_days)
    }

    override fun getItemViewType(position: Int): Int {
        val item = listPreview?.get(position)
        return item?.type?.id ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {

            ViewType.ONE.id -> {
                val view1 =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_view_header, parent, false)
                ViewHolderOne(view1)
            }

            ViewType.TWO.id,
            -> {
                val view2 =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.recycler_view_hours, parent, false)
                ViewHolderTwo(view2)
            }
            else
            -> {
                val view3 =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.recycler_view_days, parent, false)
                ViewHolderThree(view3)
            }

        }
    }

    @SuppressLint("RestrictedApi")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = listPreview?.get(position)
        val itemViewType = item?.type

        when (itemViewType) {
            ViewType.ONE -> {
                val viewHolder1 = holder as ViewHolderOne
                val first = item?.data as? Header
                viewHolder1.tvCityCountry.text =
                    first?.cityName.toString() + ", " + first?.country.toString()
                viewHolder1.tvTempCurrent.text = first?.currentTemp
                viewHolder1.tvDescription.text = first?.description
                viewHolder1.tvTempMax.text = "H:" + first?.tempMax
                viewHolder1.tvTempMin.text = "L:" + first?.tempMin



            }

            ViewType.TWO -> {
                val viewHolder2 = holder as ViewHolderTwo
                val second = item?.data as? ArrayList<Hours>

                viewHolder2.rvHours.layoutManager = LinearLayoutManager(
                    viewHolder2.rvHours.context,
                    RecyclerView.HORIZONTAL,
                    false
                )
                hoursAdapter = HoursAdapter(context, second!!)
                viewHolder2.rvHours.adapter = hoursAdapter
            }

            else -> {
                val viewHolder3 = holder as ViewHolderThree
                val third = item?.data as? ArrayList<Days>

                viewHolder3.rvDays.layoutManager = LinearLayoutManager(
                    viewHolder3.rvDays.context,
                    RecyclerView.VERTICAL,
                    false
                )
                daysAdapter = DaysAdapter(context, third)
                viewHolder3.rvDays.adapter = daysAdapter

            }


        }
    }


}



