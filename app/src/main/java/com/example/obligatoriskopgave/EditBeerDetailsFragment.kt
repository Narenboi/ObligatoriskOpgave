package com.example.obligatoriskopgave

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.obligatoriskopgave.databinding.FragmentEditBeerDetailsBinding
import com.example.obligatoriskopgave.models.Beer
import com.example.obligatoriskopgave.models.BeerViewModel

class EditBeerDetailsFragment : Fragment() {

    private var _binding: FragmentEditBeerDetailsBinding? = null
    private val binding get() = _binding!!

    private val beerViewModel: BeerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditBeerDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve Beer object from arguments
        val beer: Beer? = arguments?.getSerializable("beer") as? Beer

        // Populate EditText fields with existing Beer details and set cursor position
        beer?.let {
            binding.editTextBrewery.apply {
                setText(it.brewery)
                setCursorPositionToEnd()
            }
            binding.editTextName.apply {
                setText(it.name)
                setCursorPositionToEnd()
            }
            binding.editTextStyle.apply {
                setText(it.style)
                setCursorPositionToEnd()
            }
            binding.editTextABV.apply {
                setText(it.abv.toString())
                setCursorPositionToEnd()
            }
            binding.editTextVolume.apply {
                setText(it.volume.toString())
                setCursorPositionToEnd()
            }
            binding.editTextPictureUrl.apply {
                setText(it.pictureUrl ?: "")
                setCursorPositionToEnd()
            }
            binding.editTextHowMany.apply {
                setText(it.howMany.toString())
                setCursorPositionToEnd()
            }
        }

        // Set onClickListener to Save button
        binding.buttonSave.setOnClickListener {
            // Retrieve updated beer details from EditText fields
            val brewery = binding.editTextBrewery.text.toString()
            val name = binding.editTextName.text.toString()
            val style = binding.editTextStyle.text.toString()
            val abv = binding.editTextABV.text.toString().toDoubleOrNull() ?: 0.0
            val volume = binding.editTextVolume.text.toString().toDoubleOrNull() ?: 0.0
            val pictureUrl = binding.editTextPictureUrl.text.toString()
            val howMany = binding.editTextHowMany.text.toString().toIntOrNull() ?: 0

            // Update the beer object with the new details
            beer?.let {
                val updatedBeer = Beer(
                    it.id,
                    it.user,
                    brewery,
                    name,
                    style,
                    abv,
                    volume,
                    pictureUrl,
                    howMany
                )

                // Call updateBeer function from ViewModel
                beerViewModel.updateBeer(beerId = it.id, updatedBeer)

                // Navigate back to BeerDetailsFragment
                findNavController().navigate(R.id.action_EditBeerDetailsFragment_to_BeerFragment)

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Extension function to set cursor position to end of text in EditText
    private fun EditText.setCursorPositionToEnd() {
        setSelection(text.length)
    }
}
