package com.example.obligatoriskopgave

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.obligatoriskopgave.models.Beer
import com.example.obligatoriskopgave.models.BeerViewModel
import com.google.firebase.auth.FirebaseAuth

class AddBeerFragment : Fragment() {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    private lateinit var editTextId: EditText
    private lateinit var editTextBrewery: EditText
    private lateinit var editTextName: EditText
    private lateinit var editTextStyle: EditText
    private lateinit var editTextABV: EditText
    private lateinit var editTextVolume: EditText
    private lateinit var editTextPictureUrl: EditText
    private lateinit var editTextHowMany: EditText
    private lateinit var buttonCreate: Button
    private lateinit var beerViewModel: BeerViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.add_beer_fragment, container, false)

        // Initialize views
        editTextId = view.findViewById(R.id.editTextId)
        editTextBrewery = view.findViewById(R.id.editTextBrewery)
        editTextName = view.findViewById(R.id.editTextName)
        editTextStyle = view.findViewById(R.id.editTextStyle)
        editTextABV = view.findViewById(R.id.editTextABV)
        editTextVolume = view.findViewById(R.id.editTextVolume)
        editTextPictureUrl = view.findViewById(R.id.editTextPictureUrl)
        editTextHowMany = view.findViewById(R.id.editTextHowMany)
        buttonCreate = view.findViewById(R.id.buttonCreate)

        // Initialize ViewModel
        beerViewModel = ViewModelProvider(requireActivity()).get(BeerViewModel::class.java)

        // Set click listener for the button
        buttonCreate.setOnClickListener {
            try {
               // val id = editTextId.text.toString().toInt()

                val brewery = editTextBrewery.text.toString()
                val name = editTextName.text.toString()
                val style = editTextStyle.text.toString()
                val abv = editTextABV.text.toString().toDoubleOrNull() ?: 0.0 // Convert to Double or default to 0.0
                val volume = editTextVolume.text.toString().toDoubleOrNull() ?: 0.0
                //val pictureUrl = editTextPictureUrl.text.toString()
                val howMany = editTextHowMany.text.toString().toInt()

                val beer = Beer(0, auth.currentUser?.email, brewery, name, style, abv, volume, null, howMany)
                beerViewModel.addBeer(beer)
                beerViewModel.reload() //This is being done to manually reload the beers after adding a new one
                findNavController().navigate(R.id.action_AddBeerFragment_to_BeerFragment)

            } catch (e: NumberFormatException) {
                // Handle the error
            }
        }


        return view
    }
}
