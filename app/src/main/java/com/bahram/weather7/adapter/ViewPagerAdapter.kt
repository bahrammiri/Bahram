package com.bahram.weather7.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bahram.weather7.R
import com.bahram.weather7.model.CityItems

class ViewPagerAdapter(
    var context: Context,
    private val viewPagerList: ArrayList<CityItems>?,
) : RecyclerView.Adapter<ViewPagerAdapter.DetailViewHolder>() {

    class DetailViewHolder(
        itemView: View,
    ) : RecyclerView.ViewHolder(itemView) {
        val recyclerViewDetail: RecyclerView = itemView.findViewById(R.id.recycler_view_detail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        return DetailViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_detail_adapter, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        val item = viewPagerList?.get(position)

        holder.recyclerViewDetail.layoutManager = LinearLayoutManager(
            holder.recyclerViewDetail.context,
            RecyclerView.VERTICAL,
            false
        )
        val previewFragmentAdapter = PreviewFragmentAdapter(context, item?.cityItems, null, PreviewFragmentAdapter.VALUE_STATE_DETAIL_MODE)
        holder.recyclerViewDetail.adapter = previewFragmentAdapter
    }

    override fun getItemCount(): Int = viewPagerList?.size ?: 0
}
