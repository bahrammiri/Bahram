package com.bahram.weather7.brief

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
                val cityNameInputted = binding.editTextCityName.text.toString()

                val action = BriefFragmentDirections.actionBriefFragmentToPreviewFragment(cityNameInputted)
                Navigation.findNavController(view).navigate(action)
//                Navigation.findNavController(view).navigate(R.id.action_briefFragment_to_previewFragment, cityNameInputted)

//                val view = this.currentFocus
//                val view1 = activity?.currentFocus
//                if (view != null) {
//                    val imm: InputMethodManager =
////                        getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
//                        getSystemService((context as AppCompatActivity).INPUT_METHOD_SERVICE) as InputMethodManager
//
//                    imm.hideSoftInputFromWindow(view.windowToken, 0)
//                    binding.editTextCityName.clearFocus()
//                }
                true
            } else false

        }

        viewModel = ViewModelProvider(requireActivity()).get(BriefViewModel::class.java)

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it == true) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }

        }
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            if (it != "") {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }


        viewModel.citiesItems.observe(viewLifecycleOwner) {
//                binding.progressBar.visibility = View.VISIBLE
//                if (cities.size == viewModel.citiesItems.value?.size) {
//                    binding.progressBar.visibility = View.INVISIBLE
            binding.recyclerViewBrief.visibility = View.VISIBLE

//                    val newList: ArrayList<CityItems>? = viewModel.citiesItems.value
//                    newList.forEach { for it.cityItems. in  }

            val briefAdapter = BriefAdapter(requireContext(), viewModel.citiesItems.value)
            binding.recyclerViewBrief.adapter = briefAdapter
            binding.recyclerViewBrief.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
//                    briefAdapter.notifyDataSetChanged()
//                }
        }


        viewModel.start(requireContext())


    }


//    private fun goToPreviewFragment(cityNameInputted: String) {
//        val bundle = Bundle()
//        bundle.putString(KEY_DATA, cityNameInputted)
//        val previewFragment = PreviewFragment()
//        previewFragment.arguments = bundle
//        val transaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.fragment_container_main, previewFragment)
//        transaction.commit()
//    }


}

//ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
//    override fun onMove(
//        recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder,
//    ): Boolean {
//        return false
//    }
//
//    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//        // this method is called when we swipe our item to right direction.
//        // on below line we are getting the item at a particular position.
//        val deletedCourse =
//            cityItems?.get(viewHolder.adapterPosition)
//
//        // below line is to get the position
//        // of the item at that position.
//        val position = viewHolder.adapterPosition
//
//        // this method is called when item is swiped.
//        // below line is to remove item from our array list.
//        cityItems?.removeAt(viewHolder.adapterPosition)
//
//        // below line is to notify our item is removed from adapter.
//        briefAdapter.notifyItemRemoved(viewHolder.adapterPosition)
//
//        // below line is to display our snackbar with action.
//        // below line is to display our snackbar with action.
//        // below line is to display our snackbar with action.
//        Snackbar.make(binding.recyclerViewBrief, "Deleted " + deletedCourse?.cityItems?.getOrNull(0)?.item, Snackbar.LENGTH_LONG)
//            .setAction(
//                "Undo",
//                View.OnClickListener {
//                    // adding on click listener to our action of snack bar.
//                    // below line is to add our item to array list with a position.
//                    if (deletedCourse != null) {
//                        cityItems?.add(position, deletedCourse)
//                    }
//
//                    // below line is to notify item is
//                    // added to our adapter class.
//                    briefAdapter.notifyItemInserted(position)
//                }).show()
//    }
//    // at last we are adding this
//    // to our recycler view.
//}).attachToRecyclerView(binding.recyclerViewBrief)

