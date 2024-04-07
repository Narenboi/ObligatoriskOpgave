package com.example.obligatoriskopgave

import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.obligatoriskopgave.databinding.BeerFragmentBinding
import com.example.obligatoriskopgave.models.Beer
import com.example.obligatoriskopgave.models.BeerViewModel
import com.google.firebase.auth.FirebaseAuth

class BeerFragment : Fragment() {
    private var _binding: BeerFragmentBinding? = null
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

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

        val userEmail = auth.currentUser?.email
        if (userEmail == null) {
            findNavController().navigate(R.id.action_BeerFragment_to_LoginFragment)
            return
        }

        // Set onClickListener to Sort button
        binding.buttonSort.setOnClickListener {
            showSortOptions()
        }

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

        binding.buttonCreateNewBeer.setOnClickListener {
            findNavController().navigate(R.id.action_BeerFragment_to_AddBeerFragment)
        }
        // Observe errorMessageLiveData
        beerViewModel.errorMessageLiveData.observe(viewLifecycleOwner) { errorMessage ->
            binding.textviewMessage.text = errorMessage
        }

        // Trigger reload when fragment is resumed (back navigation)
        viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onResume(owner: LifecycleOwner) {
                super.onResume(owner)
                beerViewModel.reload()
            }
        })

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

    private fun showSortOptions() {
        val popupMenu = PopupMenu(requireContext(), binding.buttonSort)
        popupMenu.menuInflater.inflate(R.menu.sort_options_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_item_sort_alphabetical -> {
                    beerViewModel.sortAlphabetical()
                    true
                }
                R.id.menu_item_sort_volume -> {
                    beerViewModel.sortByVolume()
                    true
                }
                R.id.menu_item_sort_how_many -> {
                    beerViewModel.sortByHowMany()
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }
}
