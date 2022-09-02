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
    var context: Context,
    var cityItems: ArrayList<CityItem>?,
    private val cityResponse: WeatherResponse? = null,
    private val KEY_STATE: String? = null,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val KEY_STATE = "KEY_STATE"
        const val VALUE_STATE_DETAIL_MODE = "VALUE_STATE_DETAIL_MODE"
    }

    override fun getItemCount(): Int {
        return cityItems?.size ?: 0
    }

    class ViewHolderOne(viewOne: View) : RecyclerView.ViewHolder(viewOne) {
        val textViewCityCountry: TextView = viewOne.findViewById(R.id.text_view_city_country)
        val textViewTempCurrent: TextView = viewOne.findViewById(R.id.text_view_temp_current)
        val textViewDescription: TextView = viewOne.findViewById(R.id.text_view_description)
        val textViewTempMax: TextView = viewOne.findViewById(R.id.text_view_temp_max)
        val textViewTempMin: TextView = viewOne.findViewById(R.id.text_view_temp_min)

        val textViewAdd: TextView = viewOne.findViewById(R.id.text_view_add)
        val textViewCancel: TextView = viewOne.findViewById(R.id.text_view_cancel)
    }

    class ViewHolderTwo(viewTwo: View) : RecyclerView.ViewHolder(viewTwo) {
        val recyclerViewHours: RecyclerView = viewTwo.findViewById(R.id.recycler_view_hours)
    }

    class ViewHolderThree(viewThree: View) : RecyclerView.ViewHolder(viewThree) {
        val recyclerViewDays: RecyclerView = viewThree.findViewById(R.id.recycler_view_days)
    }


    override fun getItemViewType(position: Int): Int {
        val item = cityItems?.get(position)
        return item?.type?.id ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {

            ViewType.ONE.id -> {
                val view1 =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_view_header, parent, false)
                ViewHolderOne(view1)
            }

            ViewType.TWO.id,
            -> {
                val view2 =
                    LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_hours, parent, false)
                ViewHolderTwo(view2)
            }
            else
            -> {
                val view3 =
                    LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_days, parent, false)
                ViewHolderThree(view3)
            }

        }
    }

    @SuppressLint("RestrictedApi")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = cityItems?.get(position)

        when (item?.type) {
            ViewType.ONE -> {
                val viewHolder1 = holder as ViewHolderOne
                val header = item?.item as? Header
                viewHolder1.textViewCityCountry.text = header?.cityName.toString() + ", " + header?.country.toString()
                viewHolder1.textViewTempCurrent.text = header?.currentTemp
                viewHolder1.textViewDescription.text = header?.description
                viewHolder1.textViewTempMax.text = "H:" + header?.tempMax
                viewHolder1.textViewTempMin.text = "L:" + header?.tempMin

                if (KEY_STATE == VALUE_STATE_DETAIL_MODE) {
                    viewHolder1.textViewAdd.visibility = View.INVISIBLE
                    viewHolder1.textViewCancel.visibility = View.INVISIBLE
                } else {
                    viewHolder1.textViewAdd.setOnClickListener {
                        val sh = SharedPreferencesManager(context)
                        sh.saveCityResponse(header?.cityName.toString(), cityResponse!!)

                        val intent = Intent(context, MainActivity::class.java)
                        viewHolder1.textViewAdd.context.startActivity(intent)
                    }

                    viewHolder1.textViewCancel.setOnClickListener {
                        val intent = Intent(context, MainActivity::class.java)
                        viewHolder1.textViewCancel.context.startActivity(intent)
                    }
                }

            }

            ViewType.TWO -> {
                val viewHolder2 = holder as ViewHolderTwo
                val hour = item.item as ArrayList<Hours>

                viewHolder2.recyclerViewHours.layoutManager = LinearLayoutManager(
                    viewHolder2.recyclerViewHours.context,
                    RecyclerView.HORIZONTAL,
                    false
                )
                val hoursAdapter = HoursAdapter(context, hour!!)
                viewHolder2.recyclerViewHours.adapter = hoursAdapter
            }

            else -> {
                val viewHolder3 = holder as ViewHolderThree
                val day = item?.item as? ArrayList<Days>

                viewHolder3.recyclerViewDays.layoutManager = LinearLayoutManager(
                    viewHolder3.recyclerViewDays.context,
                    RecyclerView.VERTICAL,
                    false
                )
                val daysAdapter = DaysAdapter(context, day)
                viewHolder3.recyclerViewDays.adapter = daysAdapter

            }

        }
    }

}



