package com.bahram.weather7.preview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bahram.weather7.adapter.PreviewFragmentAdapter
import com.bahram.weather7.databinding.FragmentPreviewBinding
import com.bahram.weather7.model.WeatherResponse
import com.bahram.weather7.util.WeatherResponseItemMapper

class PreviewFragment : Fragment() {

    private lateinit var binding: FragmentPreviewBinding
    lateinit var viewModel: PreviewViewModel

    companion object {
        const val KEY_DATA = "KEY_DATA"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentPreviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cityResponse = arguments?.getParcelable<WeatherResponse>(KEY_DATA)

        viewModel = ViewModelProvider(requireActivity()).get(PreviewViewModel::class.java)
        viewModel.newResponse(cityResponse)

        viewModel.response.observe(viewLifecycleOwner) {
            val previewFragmentAdapter = PreviewFragmentAdapter(requireContext(), WeatherResponseItemMapper.loadCityItems(it), it)
            binding.recyclerViewPreview.adapter = previewFragmentAdapter
            binding.recyclerViewPreview.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

//        val previewFragmentAdapter = PreviewFragmentAdapter(requireContext(), WeatherResponseItemMapper.loadCityItems(cityResponse), cityResponse)
//        binding.recyclerViewPreview.adapter = previewFragmentAdapter
//        binding.recyclerViewPreview.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

    }
}

