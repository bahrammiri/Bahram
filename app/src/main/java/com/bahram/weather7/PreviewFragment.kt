package com.bahram.weather7

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bahram.weather7.adapter.PreviewFragmentAdapter
import com.bahram.weather7.model.*


class PreviewFragment : Fragment() {

    lateinit var recyclerViewPreview: RecyclerView
    lateinit var previewFragmentAdapter: PreviewFragmentAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val viewPF = LayoutInflater.from(requireContext()).inflate(R.layout.preview_fragment, container, false)
        recyclerViewPreview = viewPF.findViewById(R.id.recycler_view_preview)
        return viewPF
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var selectedListReceived = arguments?.getParcelableArrayList<Item>("For PreviewFragment")
        Log.i("For PreviewFragment", "$selectedListReceived")

        previewFragmentAdapter = PreviewFragmentAdapter(requireContext(), selectedListReceived as ArrayList<Item>?)
        recyclerViewPreview.adapter = previewFragmentAdapter
        recyclerViewPreview.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

    }


}

