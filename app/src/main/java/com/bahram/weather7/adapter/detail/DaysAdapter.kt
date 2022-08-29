package com.bahram.weather7.adapter.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bahram.weather7.R
import com.bahram.weather7.model.Days
import com.bumptech.glide.Glide

class DaysAdapter(val context: Context, private val daysList: ArrayList<Days>?) :
    RecyclerView.Adapter<DaysAdapter.ViewHolderDays>() {
    class ViewHolderDays(view: View) : RecyclerView.ViewHolder(view) {
        var textViewNameDay: TextView = view.findViewById(R.id.text_view_name_day)
        var imageViewIconDay: ImageView = view.findViewById(R.id.image_view_icon_day)
        var textViewTempMinDay: TextView = view.findViewById(R.id.text_view_temp_min_day)
        var textViewTempMaxDay: TextView = view.findViewById(R.id.text_view_temp_max_day)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderDays {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_days, parent, false)
        return ViewHolderDays(view)
    }

    override fun onBindViewHolder(holder: ViewHolderDays, position: Int) {
        val item = daysList?.get(position)

        holder.textViewNameDay.text = item?.day
        Glide.with(context)
            .load(item?.iconDay)
            .into(holder.imageViewIconDay)
        holder.textViewTempMinDay.text = item?.tempMinDay
        holder.textViewTempMaxDay.text = item?.tempMaxDay
    }

    override fun getItemCount(): Int {
        return daysList?.size ?: 0
    }

}