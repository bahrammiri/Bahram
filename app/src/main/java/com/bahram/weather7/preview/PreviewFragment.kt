package com.bahram.weather7.preview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bahram.weather7.adapter.PreviewAdapter
import com.bahram.weather7.databinding.FragmentPreviewBinding

class PreviewFragment : Fragment() {

    private lateinit var binding: FragmentPreviewBinding
    lateinit var viewModel: PreviewViewModel

    val args: PreviewFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentPreviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cityNameInputted = args.cityNameIputted1

        viewModel = ViewModelProvider(requireActivity()).get(PreviewViewModel::class.java)

        viewModel.cityItems.observe(viewLifecycleOwner) {
            val previewAdapter = PreviewAdapter(requireContext(), viewModel.cityItems.value, null)
            binding.recyclerViewPreview.adapter = previewAdapter
            binding.recyclerViewPreview.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            previewAdapter.notifyDataSetChanged()
        }
        cityNameInputted?.let { viewModel.loadCityItems(it) }
//        viewModel.loadCityItems(cityNameInputted)

//        val previewFragmentAdapter = PreviewAdapter(requireContext(), WeatherResponseItemMapper.loadCityItems(cityResponse), cityResponse)
//        binding.recyclerViewPreview.adapter = previewFragmentAdapter
//        binding.recyclerViewPreview.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

    }

}

