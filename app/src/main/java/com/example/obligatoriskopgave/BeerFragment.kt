package com.example.obligatoriskopgave


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
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

        // Observe beersLiveData
        beerViewModel.beersLiveData.observe(viewLifecycleOwner) { beers ->
            beers?.let {
                val adapter = MyAdapter(beers) { position ->
                    val beer: Beer? = beerViewModel.get(position)
                    beer?.let {
                        navigateToBeerDetails(beer)
                    }
                }
                binding.recyclerView.layoutManager = LinearLayoutManager(activity)
                binding.recyclerView.adapter = adapter
            }
        }

        // Observe errorMessageLiveData
        beerViewModel.errorMessageLiveData.observe(viewLifecycleOwner) { errorMessage ->
            binding.textviewMessage.text = errorMessage
        }

        // Trigger reload when swiping
        binding.swiperefresh.setOnRefreshListener {
            beerViewModel.reload()
            binding.swiperefresh.isRefreshing = false
        }

        // Observe reloadingLiveData to show/hide loading indicator
        beerViewModel.reloadingLiveData.observe(viewLifecycleOwner) { isLoading ->
            binding.swiperefresh.isRefreshing = isLoading
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateToBeerDetails(beer: Beer) {
        val action = BeerFragmentDirections.actionBeerFragmentToBeerDetailsFragment(beer)
        findNavController().navigate(action)
    }
}
