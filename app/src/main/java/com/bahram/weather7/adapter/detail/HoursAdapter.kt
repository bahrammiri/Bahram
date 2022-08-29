package com.bahram.weather7.adapter.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bahram.weather7.R
import com.bahram.weather7.model.Hours
import com.bumptech.glide.Glide

class HoursAdapter(val context: Context, private val hoursList: ArrayList<Hours>) :
    RecyclerView.Adapter<HoursAdapter.ViewHolderHours>() {
    class ViewHolderHours(view: View) : RecyclerView.ViewHolder(view) {
        var textViewHour: TextView = view.findViewById(R.id.text_view_hour)
        var imageViewIconHour: ImageView = view.findViewById(R.id.image_view_icon_hour)
        var textViewTempHour: TextView = view.findViewById(R.id.text_view_temp_hour)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderHours {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_hours, parent, false)
        return ViewHolderHours(view)
    }

    override fun onBindViewHolder(holder: ViewHolderHours, position: Int) {
        val item = hoursList.get(position)

        holder.textViewHour.text = item.hour
        Glide.with(context)
            .load(item.icon)
            .into(holder.imageViewIconHour)
        holder.textViewTempHour.text = item.temp
    }

    override fun getItemCount(): Int {
        return hoursList.size
    }

}