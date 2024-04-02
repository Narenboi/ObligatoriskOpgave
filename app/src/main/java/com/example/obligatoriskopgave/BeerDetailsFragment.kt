package com.example.obligatoriskopgave


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.example.obligatoriskopgave.databinding.FragmentBeerDetailsBinding
import com.example.obligatoriskopgave.models.Beer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController

class BeerDetailsFragment : Fragment() {

    private var _binding: FragmentBeerDetailsBinding? = null
    private val binding get() = _binding!!

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
        }
    }





    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
