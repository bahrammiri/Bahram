package com.bahram.weather7.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bahram.weather7.databinding.ItemDetailAdapterBinding
import com.bahram.weather7.model.CityItems

class DetailAdapter(
    var context: Context,
    private val viewPagerList: ArrayList<CityItems>?,
) : RecyclerView.Adapter<DetailAdapter.DetailViewHolder>() {

//    class DetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val recyclerViewDetail: RecyclerView = itemView.findViewById(R.id.recycler_view_detail)
//    }

    class DetailViewHolder(val binding: ItemDetailAdapterBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
//        return DetailViewHolder(
//            LayoutInflater.from(parent.context).inflate(R.layout.item_detail_adapter, parent, false)
//        )

        val binding = ItemDetailAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        val item = viewPagerList?.get(position)

        holder.binding.recyclerViewDetail.layoutManager = LinearLayoutManager(
            holder.binding.recyclerViewDetail.context,
            RecyclerView.VERTICAL,
            false
        )
        val previewAdapter = PreviewAdapter(context, item?.cityItems, PreviewAdapter.VALUE_STATE_DETAIL_MODE)
        holder.binding.recyclerViewDetail.adapter = previewAdapter
    }

    override fun getItemCount(): Int = viewPagerList?.size ?: 0
}
