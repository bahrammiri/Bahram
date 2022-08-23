package com.bahram.weather7.adapter.detail

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bahram.weather7.R
import com.bahram.weather7.model.Days
import com.bumptech.glide.Glide

class DaysAdapter(val context1: Context, val items3: ArrayList<Days>?) : RecyclerView.Adapter<DaysAdapter.ViewHolderDays>() {
    class ViewHolderDays(view1: View) : RecyclerView.ViewHolder(view1) {
        var tvDayName: TextView = view1.findViewById(R.id.tv_day_name)
        var ivIconDay: ImageView = view1.findViewById(R.id.iv_icon_day)
        var tvTempMinDay: TextView = view1.findViewById(R.id.tv_temp_min_day)
        var tvTempMaxDay: TextView = view1.findViewById(R.id.tv_temp_max_day)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderDays {
        var view1 = LayoutInflater.from(parent.context).inflate(R.layout.item_view_days, parent, false)
        return ViewHolderDays(view1)
    }

    override fun onBindViewHolder(holder: ViewHolderDays, position: Int) {
        val item = items3?.get(position)

        holder.tvDayName.text = item?.day
        Log.i("itemDataday", "$item")

        val iconCode = item?.iconDay.toString()
        Glide.with(context1)
            .load(item?.iconDay)
            .into(holder.ivIconDay)

        holder.tvTempMinDay.text = item?.tempMinDay
        holder.tvTempMaxDay.text = item?.tempMaxDay
    }

    override fun getItemCount(): Int {
        return items3?.size ?: 0
    }

}