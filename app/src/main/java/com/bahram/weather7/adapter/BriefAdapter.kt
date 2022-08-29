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
import com.bahram.weather7.ViewPagerActivity
import com.bahram.weather7.R
import com.bahram.weather7.model.Final
import com.bahram.weather7.model.Header
import com.bahram.weather7.model.ViewType

class BriefAdapter(var context: Context, var briefcitieslist: ArrayList<Final>?, val onClickListener: OnClickListener? = null) :
    RecyclerView.Adapter<BriefAdapter.ViewHolderOne>() {

    override fun getItemCount(): Int {
        return briefcitieslist?.size ?: 0
    }

    class ViewHolderOne(viewOne: View) : RecyclerView.ViewHolder(viewOne) {
        val llSelected: LinearLayout = viewOne.findViewById(R.id.linear_layout_brief)
        val textViewCityBrief: TextView = viewOne.findViewById(R.id.text_view_city_brief)
        val textViewDescriptionBrief: TextView = viewOne.findViewById(R.id.text_view_description_brief)
        val textViewTempBrief: TextView = viewOne.findViewById(R.id.text_view_temp_brief)
        val textViewTempMaxBrief: TextView = viewOne.findViewById(R.id.text_view_temp_max_brief)
//        val tvTempMinB: TextView = viewOne.findViewById(R.id.tv_temp)

        //
        fun bind(
            final: Final,
            onClickListener: OnClickListener?,
        ) {
            val final1 = final?.items?.getOrNull(0)
            if (final1?.type == ViewType.ONE) {

                val headerData = final1.data as Header
                textViewCityBrief.text = headerData.cityName
                textViewTempBrief.text = headerData.currentTemp
                textViewDescriptionBrief.text = headerData.description
                textViewTempMaxBrief.text = headerData.tempMax

                itemView.setOnClickListener {
                    onClickListener?.onClick(final)
                }
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
        val item = briefcitieslist?.get(position)
        if (item != null) {
            holder.bind(item, onClickListener)
        }

        holder.llSelected.setOnClickListener {
            var intent = Intent(context, ViewPagerActivity::class.java)
            intent.putExtra("briefcitieslist", position)
            holder.llSelected.context.startActivity(intent)

        }
    }

    class OnClickListener(val clickListener: (final: Final) -> Unit) {
        fun onClick(final: Final) = clickListener(final)
    }

}


