package com.example.obligatoriskopgave.models

import BeerRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.obligatoriskopgave.models.Beer

class BeerViewModel : ViewModel()  {

    private val repository = BeerRepository()
    val beersLiveData: LiveData<List<Beer>> = repository.beersLiveData
    val errorMessageLiveData: LiveData<String> = repository.errorMessageLiveData
    val updateMessageLiveData: LiveData<String> = repository.updateMessageLiveData
    val reloadingLiveData: LiveData<Boolean> = repository.reloadingLiveData
    val deleteBeerErrorMessageLiveData: LiveData<String> = repository.deleteBeerErrorMessageLiveData
    val beerLiveData: LiveData<List<Beer>> = repository.beerLiveData


    // Method to reload beers
    fun reload() {
        repository.getBeers()
    }

    // Method to add a new beer
    fun addBeer(beer: Beer) {
        repository.addBeer(beer)
    }

    // Initialization
    init {
        reload()
    }

    // Access operator for getting beer by index
    operator fun get(index: Int): Beer? {
        return beersLiveData.value?.get(index)
    }
}
