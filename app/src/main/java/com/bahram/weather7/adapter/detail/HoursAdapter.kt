package com.bahram.weather7.adapter.detail

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bahram.weather7.model.Hours
import com.bahram.weather7.R

import com.bumptech.glide.Glide

class HoursAdapter(val context1: Context, val items: ArrayList<Hours>) : RecyclerView.Adapter<HoursAdapter.ViewHolderHours>() {
    class ViewHolderHours(view1: View) : RecyclerView.ViewHolder(view1) {
        var tvHour: TextView = view1.findViewById(R.id.tv_hour)
        var ivIcon: ImageView = view1.findViewById(R.id.iv_icon)
        var tvTemp: TextView = view1.findViewById(R.id.tv_temp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderHours {
        var view1 = LayoutInflater.from(parent.context).inflate(R.layout.item_view_hours, parent, false)
        return ViewHolderHours(view1)
    }

    override fun onBindViewHolder(holder: ViewHolderHours, position: Int) {
        val item = items.get(position)

        holder.tvHour.text = item.hour

        Glide.with(context1)
            .load(item.icon)
            .into(holder.ivIcon)

        holder.tvTemp.text = item?.temp
    }

    override fun getItemCount(): Int {
        return items.size
    }

}