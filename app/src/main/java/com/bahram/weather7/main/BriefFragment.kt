package com.bahram.weather7.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bahram.weather7.adapter.BriefAdapter
import com.bahram.weather7.databinding.FragmentBriefBinding
import com.bahram.weather7.model.CityItems
import com.bahram.weather7.util.WeatherResponseItemMapper
import com.google.android.material.snackbar.Snackbar

class BriefFragment : Fragment() {

    private lateinit var binding: FragmentBriefBinding
    private lateinit var briefAdapter: BriefAdapter
    var citiesItems: ArrayList<CityItems>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentBriefBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        citiesItems = WeatherResponseItemMapper.loadCitiesItems(requireContext())

        //***
        binding.recyclerViewBrief.visibility = View.VISIBLE
        briefAdapter = BriefAdapter(requireContext(), citiesItems)
        binding.recyclerViewBrief.adapter = briefAdapter
        binding.recyclerViewBrief.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        briefAdapter.notifyDataSetChanged()


        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder,
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // this method is called when we swipe our item to right direction.
                // on below line we are getting the item at a particular position.
                val deletedCourse =
                    citiesItems?.get(viewHolder.adapterPosition)

                // below line is to get the position
                // of the item at that position.
                val position = viewHolder.adapterPosition

                // this method is called when item is swiped.
                // below line is to remove item from our array list.
                citiesItems?.removeAt(viewHolder.adapterPosition)

                // below line is to notify our item is removed from adapter.
                briefAdapter.notifyItemRemoved(viewHolder.adapterPosition)

                // below line is to display our snackbar with action.
                // below line is to display our snackbar with action.
                // below line is to display our snackbar with action.
                Snackbar.make(binding.recyclerViewBrief, "Deleted " + deletedCourse?.cityItems?.getOrNull(0)?.item, Snackbar.LENGTH_LONG)
                    .setAction(
                        "Undo",
                        View.OnClickListener {
                            // adding on click listener to our action of snack bar.
                            // below line is to add our item to array list with a position.
                            if (deletedCourse != null) {
                                citiesItems?.add(position, deletedCourse)
                            }

                            // below line is to notify item is
                            // added to our adapter class.
                            briefAdapter.notifyItemInserted(position)
                        }).show()
            }
            // at last we are adding this
            // to our recycler view.
        }).attachToRecyclerView(binding.recyclerViewBrief)

        //***


//        val previewFragmentAdapter = PreviewFragmentAdapter(requireContext(), WeatherResponseItemMapper.loadCityItems(cityResponse), cityResponse)
//        binding.recyclerViewPreview.adapter = previewFragmentAdapter
//        binding.recyclerViewPreview.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

    }
}
