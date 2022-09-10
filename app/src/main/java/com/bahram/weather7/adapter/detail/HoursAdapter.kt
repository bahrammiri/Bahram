package com.bahram.weather7.adapter.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bahram.weather7.databinding.ItemViewHoursBinding
import com.bahram.weather7.model.Hours
import com.bumptech.glide.Glide

class HoursAdapter(private val context: Context, private val hoursList: ArrayList<Hours>) :
    RecyclerView.Adapter<HoursAdapter.ViewHolderHours>() {
    class ViewHolderHours(val binding: ItemViewHoursBinding) : RecyclerView.ViewHolder(binding.root) {
//        var textViewHour: TextView = view.findViewById(R.id.text_view_hour)
//        var imageViewIconHour: ImageView = view.findViewById(R.id.image_view_icon_hour)
//        var textViewTempHour: TextView = view.findViewById(R.id.text_view_temp_hour)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderHours {
//        val view =
//            LayoutInflater.from(parent.context).inflate(R.layout.item_view_hours, parent, false)
//        return ViewHolderHours(view)

        val binding = ItemViewHoursBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HoursAdapter.ViewHolderHours(binding)

    }

    override fun onBindViewHolder(holder: ViewHolderHours, position: Int) {
        val item = hoursList[position]

        holder.binding.textViewHour.text = item.hour
        Glide.with(context)
            .load(item.icon)
            .into(holder.binding.imageViewIconHour)
        holder.binding.textViewTempHour.text = item.temp
    }

    override fun getItemCount(): Int {
        return hoursList.size
    }

}