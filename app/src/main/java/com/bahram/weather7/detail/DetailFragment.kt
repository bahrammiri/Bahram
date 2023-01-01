package com.bahram.weather7.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bahram.weather7.adapter.DetailAdapter
import com.bahram.weather7.databinding.FragmentDetailBinding
import com.google.android.material.tabs.TabLayoutMediator

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var viewModel: DetailViewModel


    companion object {
        const val KEY_CITY_ITEM_POSITION = "KEY_CITY_ITEM_POSITION"
    }

    val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonBack.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }

        val position = args.positionItem
        viewModel = ViewModelProvider(requireActivity()).get(DetailViewModel::class.java)

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it == true) {
                binding.progressBarDetail.visibility = View.VISIBLE
                binding.viewPager2.visibility = View.GONE

            } else {
                binding.progressBarDetail.visibility = View.GONE
                binding.viewPager2.visibility = View.VISIBLE
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            if (it != "") {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.citiesItems.observe(viewLifecycleOwner) {

            binding.viewPager2.setCurrentItem(position, false)
            val detailAdapter = DetailAdapter(requireContext(), viewModel.citiesItems.value)
            binding.viewPager2.adapter = detailAdapter

            val tabLayoutMediator = TabLayoutMediator(binding.tabLayout, binding.viewPager2, true
            ) { tab, position -> }
            tabLayoutMediator.attach()

        }

        viewModel.start(requireContext())

    }

}


