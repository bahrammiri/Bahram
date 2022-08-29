package com.bahram.weather7.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bahram.weather7.MainActivity
import com.bahram.weather7.R
import com.bahram.weather7.adapter.detail.DaysAdapter
import com.bahram.weather7.adapter.detail.HoursAdapter
import com.bahram.weather7.model.*
import com.bahram.weather7.util.SharedPreferencesManager

class PreviewFragmentAdapter(
    val response: WeatherResponse?,
    var context: Context,
    var cityList: ArrayList<Item>?,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var hoursAdapter: HoursAdapter
    lateinit var daysAdapter: DaysAdapter

    override fun getItemCount(): Int {
        return cityList?.size ?: 0
    }

    class ViewHolderOne(viewOne: View) : RecyclerView.ViewHolder(viewOne) {
        val textViewCityCountry: TextView = viewOne.findViewById<TextView>(R.id.text_view_city_country)
        val textViewTempCurrent: TextView = viewOne.findViewById<TextView>(R.id.text_view_temp_current)
        val textViewDescription: TextView = viewOne.findViewById<TextView>(R.id.text_view_description)
        val textViewTempMax: TextView = viewOne.findViewById<TextView>(R.id.text_view_temp_max)
        val textViewTempMin: TextView = viewOne.findViewById<TextView>(R.id.text_view_temp_min)

        val textViewAdd: TextView = viewOne.findViewById(R.id.text_view_add)
        val textViewCancel: TextView = viewOne.findViewById(R.id.text_view_cancel)
    }

    class ViewHolderTwo(viewTwo: View) : RecyclerView.ViewHolder(viewTwo) {
        val recyclerViewHours = viewTwo.findViewById<RecyclerView>(R.id.recycler_view_hours)
    }

    class ViewHolderThree(viewThree: View) : RecyclerView.ViewHolder(viewThree) {
        val recyclerViewDays = viewThree.findViewById<RecyclerView>(R.id.recycler_view_days)
    }


    override fun getItemViewType(position: Int): Int {
        val item = cityList?.get(position)
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
        val item = cityList?.get(position)
        val itemViewType = item?.type

        when (itemViewType) {
            ViewType.ONE -> {
                val viewHolder1 = holder as ViewHolderOne
                val first = item?.data as? Header
                viewHolder1.textViewCityCountry.text =
                    first?.cityName.toString() + ", " + first?.country.toString()
                viewHolder1.textViewTempCurrent.text = first?.currentTemp
                viewHolder1.textViewDescription.text = first?.description
                viewHolder1.textViewTempMax.text = "H:" + first?.tempMax
                viewHolder1.textViewTempMin.text = "L:" + first?.tempMin

                viewHolder1.textViewAdd.setOnClickListener {

                    val sh = SharedPreferencesManager(context)
                    sh.saveCity(first?.cityName.toString(), response!!)

                    var intent = Intent(context, MainActivity::class.java)
                    viewHolder1.textViewAdd.context.startActivity(intent)
                }

                viewHolder1.textViewCancel.setOnClickListener {
                    var intent: Intent = Intent(context, MainActivity::class.java)
                    intent.putExtra("For MainActivity: cityNameSelected", "")
                    viewHolder1.textViewCancel.context.startActivity(intent)
                }

            }

            ViewType.TWO -> {
                val viewHolder2 = holder as ViewHolderTwo
                val second = item?.data as? ArrayList<Hours>

                viewHolder2.recyclerViewHours.layoutManager = LinearLayoutManager(
                    viewHolder2.recyclerViewHours.context,
                    RecyclerView.HORIZONTAL,
                    false
                )
                hoursAdapter = HoursAdapter(context, second!!)
                viewHolder2.recyclerViewHours.adapter = hoursAdapter
            }

            else -> {
                val viewHolder3 = holder as ViewHolderThree
                val third = item?.data as? ArrayList<Days>

                viewHolder3.recyclerViewDays.layoutManager = LinearLayoutManager(
                    viewHolder3.recyclerViewDays.context,
                    RecyclerView.VERTICAL,
                    false
                )
                daysAdapter = DaysAdapter(context, third)
                viewHolder3.recyclerViewDays.adapter = daysAdapter

            }

        }
    }

}



