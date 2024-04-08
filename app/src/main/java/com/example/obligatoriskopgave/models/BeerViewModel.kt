package com.example.obligatoriskopgave.models

import BeerRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class BeerViewModel : ViewModel() {

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

    fun deleteBeer(beerId: Int) {
        repository.deleteBeer(beerId)
    }

    fun updateBeer(beerId: Int, beer: Beer){
        repository.updateBeer(beerId, beer)
    }

    // Initialization
    init {
        reload()
    }

    // Access operator for getting beer by index
    operator fun get(index: Int): Beer? {
        return beersLiveData.value?.get(index)
    }


    // Function to sort beers alphabetically by name
    fun sortAlphabetical() {
        val currentBeers = beersLiveData.value ?: return
        val sortedBeers = currentBeers.sortedBy { it.name }
        repository.updateBeersLiveData(sortedBeers)
    }

    // Function to sort beers by volume
    fun sortByVolume() {
        val currentBeers = beersLiveData.value ?: return
        val sortedBeers = currentBeers.sortedBy { it.volume }
        repository.updateBeersLiveData(sortedBeers)
    }

    // Function to sort beers by how many
    fun sortByHowMany() {
        val currentBeers = beersLiveData.value ?: return
        val sortedBeers = currentBeers.sortedBy { it.howMany }
        repository.updateBeersLiveData(sortedBeers)
    }

    // Function to filter beers alphabetically by name
    fun filterAlphabetical() {
        val currentBeers = beersLiveData.value ?: return
        val sortedBeers = currentBeers.sortedBy { it.name }
        repository.updateBeersLiveData(sortedBeers)
    }

    // Function to filter beers by volume
    fun filterByVolume() {
        val currentBeers = beersLiveData.value ?: return
        val sortedBeers = currentBeers.sortedBy { it.volume }
        repository.updateBeersLiveData(sortedBeers)
    }

    // Function to filter beers by how many
    fun filterByHowMany() {
        val currentBeers = beersLiveData.value ?: return
        val sortedBeers = currentBeers.sortedBy { it.howMany }
        repository.updateBeersLiveData(sortedBeers)
    }

}
