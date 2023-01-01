package com.bahram.weather7.brief

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bahram.weather7.SwipeToDeleteCallback
import com.bahram.weather7.adapter.BriefAdapter
import com.bahram.weather7.databinding.FragmentBriefBinding

class BriefFragment : Fragment() {
    lateinit var viewModel: BriefViewModel
    private lateinit var binding: FragmentBriefBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentBriefBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editTextCityName.setOnEditorActionListener { v, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                val viewCurrent = requireActivity().currentFocus
                if (viewCurrent != null) {
                    val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(viewCurrent.windowToken, 0)
                    binding.editTextCityName.clearFocus()

                    if (binding.editTextCityName.text.toString() != "") {
                        val action = BriefFragmentDirections.actionBriefFragmentToPreviewFragment(binding.editTextCityName.text.toString())
                        binding.editTextCityName.setText("")
                        Navigation.findNavController(view).navigate(action)
                    }
                }

                true
            } else false

        }

        viewModel = ViewModelProvider(requireActivity()).get(BriefViewModel::class.java)

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it == true) {
                binding.progressBarBrief.visibility = View.VISIBLE
                binding.recyclerViewBrief.visibility = View.GONE
            } else {
                binding.progressBarBrief.visibility = View.GONE
                binding.recyclerViewBrief.visibility = View.VISIBLE
            }

        }
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            if (it != "") {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.citiesItems.observe(viewLifecycleOwner) {
            val briefAdapter = BriefAdapter(requireContext(), viewModel.citiesItems.value)
            binding.recyclerViewBrief.adapter = briefAdapter
            binding.recyclerViewBrief.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

            val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {

                override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                    Log.i("rasoul", "${viewHolder.adapterPosition}")

                    viewModel.removeCityFromCitiesItems(viewHolder.adapterPosition, requireContext())
                    briefAdapter.notifyItemRemoved(viewHolder.adapterPosition)

                }
            }


            val itemTouchHelper = ItemTouchHelper(swipeHandler)
            itemTouchHelper.attachToRecyclerView(binding.recyclerViewBrief)

        }

        viewModel.start(requireContext())

    }


}


