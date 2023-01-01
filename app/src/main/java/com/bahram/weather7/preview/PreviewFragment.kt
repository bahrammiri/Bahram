package com.bahram.weather7.preview

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bahram.weather7.adapter.PreviewAdapter
import com.bahram.weather7.databinding.FragmentPreviewBinding
import com.bahram.weather7.model.CityItem
import com.bahram.weather7.model.Header
import com.bahram.weather7.util.SharedPreferencesManager


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

        makeToast("lets start")

        val toast = Toast.makeText(getContext(), "text", Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
        toast.show()

        val cityNameInputted = args.cityNameIputted1

        viewModel = ViewModelProvider(requireActivity()).get(PreviewViewModel::class.java)

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            if (it != "") {
//                Toast.makeText(getActivity()?.applicationContext, "fbcbbc", Toast.LENGTH_SHORT).show()
//                binding.textViewCancel.setText("$it")
                binding.textViewError.visibility = View.VISIBLE
                binding.textViewError.setText(it)
            }
        }





        viewModel.cityItems.observe(viewLifecycleOwner) {

            if (viewModel.cityItems.value != emptyArray<ArrayList<CityItem>>()) {

                val previewAdapter = PreviewAdapter(requireContext(), viewModel.cityItems.value, null)
                binding.recyclerViewPreview.adapter = previewAdapter
                binding.recyclerViewPreview.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)


                previewAdapter.notifyDataSetChanged()
            }
        }
        viewModel.loadCityItems(cityNameInputted)
//        cityNameInputted?.let { viewModel.loadCityItems(it) }

//viewModel.cityNameInputted = cityNameInputted


        binding.textViewAdd.setOnClickListener {
            val sh = SharedPreferencesManager(requireContext())
            sh.saveCityName((viewModel.cityItems.value?.getOrNull(0)?.item as Header).cityName!!,
                (viewModel.cityItems.value?.getOrNull(0)?.item as Header).cityName!!)
            findNavController().navigateUp()
        }

        binding.textViewCancel.setOnClickListener {
            findNavController().navigateUp()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().getViewModelStore().clear()
    }

    fun Fragment.makeToast(text: String, duration: Int = Toast.LENGTH_LONG) {
        activity?.let {
            Toast.makeText(it, text, duration).show()
        }
    }
}



