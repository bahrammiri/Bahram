package com.bahram.weather7.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bahram.weather7.R
import com.bahram.weather7.model.Final

//lateinit var mainAdapter: MainAdapter
lateinit var previewFragmentAdapter: PreviewFragmentAdapter

class DetailViewPagerAdapter(
    var context: Context,
    private val viewPagerList: ArrayList<Final>?,
) : RecyclerView.Adapter<DetailViewPagerAdapter.DetailViewHolder>() {

    class DetailViewHolder(
        itemView: View,
    ) : RecyclerView.ViewHolder(itemView) {
        val recyclerViewDetail2: RecyclerView = itemView.findViewById(R.id.recycler_view_detail)
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

        holder.recyclerViewDetail2.layoutManager = LinearLayoutManager(
            holder.recyclerViewDetail2.context,
            RecyclerView.VERTICAL,
            false
        )
        previewFragmentAdapter = PreviewFragmentAdapter(null,context, item?.items)
        holder.recyclerViewDetail2.adapter = previewFragmentAdapter


    }

    override fun getItemCount(): Int = viewPagerList?.size ?: 0
}
