package com.example.obligatoriskopgave

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.obligatoriskopgave.databinding.FragmentBeerDetailsBinding
import com.example.obligatoriskopgave.models.Beer
import com.example.obligatoriskopgave.models.BeerViewModel

class BeerDetailsFragment : Fragment() {

    private var _binding: FragmentBeerDetailsBinding? = null
    private val binding get() = _binding!!

    private val beerViewModel: BeerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBeerDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve Beer object from arguments
        val beer: Beer? = arguments?.getSerializable("beer") as? Beer

        // Populate UI with Beer details
        beer?.let {
            binding.textViewBreweryValue.text = it.brewery
            binding.textViewNameValue.text = it.name
            binding.textViewABVValue.text = it.abv.toString()
            binding.textViewStyleValue.text = it.style
            binding.textViewVolumeValue.text = it.volume.toString()
            binding.textViewHowManyValue.text = it.howMany.toString()
        }

        // Set onClickListener to Delete Beer button
        binding.buttonDelete.setOnClickListener {
            // Retrieve beer ID
            val beerId = beer?.id ?: return@setOnClickListener

            // Call deleteBeer function from ViewModel
            beerViewModel.deleteBeer(beerId)

            // Navigate back to BeerFragment
            findNavController().navigateUp()
        }

        binding.buttonEdit.setOnClickListener() {
            // Navigate to EditBeerDetailsFragment
            findNavController().navigate(R.id.action_beerDetailsFragment_to_EditBeerDetailsFragment, Bundle().apply {
                putSerializable("beer", beer)
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
