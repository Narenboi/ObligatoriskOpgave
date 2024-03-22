package com.example.obligatoriskopgave

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.obligatoriskopgave.databinding.BeerFragmentBinding
import com.example.obligatoriskopgave.models.Beer
import com.example.obligatoriskopgave.models.BeerViewModel

class BeerFragment : Fragment() {
    private var _binding: BeerFragmentBinding? = null
    private val binding get() = _binding!!
    private val beerViewModel: BeerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BeerFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        beerViewModel.errorMessageLiveData.observe(viewLifecycleOwner) { errorMessage ->
        }
        beerViewModel.beersLiveData.observe(viewLifecycleOwner) { beers ->
            if (beers == null) {
            } else {
                val adapter = MyAdapter(beers) { position ->
                    val beer: Beer? = beerViewModel.get(position)
                    if (beer == null) {
                        return@MyAdapter
                    }
                    Log.d("Her sendt", beer.toString())
                    val action = BeerFragmentDirections.actionFrontpageFragmentToBeerDetailsFragment(beer.id)
                    findNavController().navigate(action)
                }
                binding.recyclerView.layoutManager = LinearLayoutManager(activity)
                binding.recyclerView.adapter = adapter
            }
        }

        beerViewModel.errorMessageLiveData.observe(viewLifecycleOwner) { errorMessage ->
            binding.textviewMessage.text = errorMessage
        }

        beerViewModel.reload()

        binding.swiperefresh.setOnRefreshListener {
            beerViewModel.reload()
            binding.swiperefresh.isRefreshing = false // TODO too early
        }

        beerViewModel.reloadingLiveData.observe(viewLifecycleOwner) { isLoading ->
            binding.swiperefresh.isRefreshing = isLoading
        }

        beerViewModel.beersLiveData.observe(viewLifecycleOwner) { beers ->
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, beers)
            //binding.spinnerBooks.adapter = adapter
            /* binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                 override fun onItemSelected(
                     parent: AdapterView<*>?,
                     view: View?,
                     position: Int,
                     id: Long
                 ) { // reacts instantly: Much to quick.
                     val action =
                         FirstFragmentDirections.actionFirstFragmentToSecondFragment(position)
                     findNavController().navigate(action /*R.id.action_FirstFragment_to_SecondFragment*/)
                 }
<Spinner
            android:id="@+id/spinner_sorting"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="2"
            android:entries="@array/sortings" />
        <!-- entries defines in values / strings.xml -->
                 override fun onNothingSelected(parent: AdapterView<*>?) {
                     TODO("Not yet implemented")
                 }
             }*/
        }

        /*binding.buttonShowDetails.setOnClickListener {
            val position = binding.spinnerBooks.selectedItemPosition
            val action =
                FirstFragmentDirections.actionFirstFragmentToSecondFragment(position)
            findNavController().navigate(action /*R.id.action_FirstFragment_to_SecondFragment*/)
        }*/

       /* binding.buttonSort.setOnClickListener {
            when (binding.spinnerSorting.selectedItemPosition) {
                0 -> beersViewModel.sortByTitle()
                1 -> beersViewModel.sortByTitleDescending()
                2 -> beersViewModel.sortByPrice()
                3 -> beersViewModel.sortByPriceDescending()
            }
        }

        binding.searchviewFilterTitle.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isEmpty()) {
                    beersViewModel.reload()
                    return false
                }
                binding.searchviewFilterTitle.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                /*if (newText.isNullOrEmpty()) {
                    beersViewModel.reload()
                    return false
                }*/
                beersViewModel.filterByTitle(newText.trim())
                return true
            }

        })

        binding.buttonFilter.setOnClickListener {
            //val title = binding.edittextFilterTitle.text.toString().trim()
            val title = binding.searchviewFilterTitle.query.toString().trim()
            /* if (title.isBlank()) {
                 binding.edittextFilterTitle.error = "No title"
                 return@setOnClickListener
             }*/
            beersViewModel.filterByTitle(title)
        }
    }*/

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}