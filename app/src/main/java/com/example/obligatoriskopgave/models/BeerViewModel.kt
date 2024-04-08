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

    fun reload() {
        repository.getBeers()
    }

    fun addBeer(beer: Beer) {
        repository.addBeer(beer)
    }

    fun deleteBeer(beerId: Int) {
        repository.deleteBeer(beerId)
    }

    fun updateBeer(beerId: Int, beer: Beer){
        repository.updateBeer(beerId, beer)
    }

    init {
        reload()
    }

    operator fun get(index: Int): Beer? {
        return beersLiveData.value?.get(index)
    }


    fun sortAlphabetical() {
        val currentBeers = beersLiveData.value ?: return
        val sortedBeers = currentBeers.sortedBy { it.name }
        repository.updateBeersLiveData(sortedBeers)
    }

    fun sortByVolume() {
        val currentBeers = beersLiveData.value ?: return
        val sortedBeers = currentBeers.sortedBy { it.volume }
        repository.updateBeersLiveData(sortedBeers)
    }

    fun sortByHowMany() {
        val currentBeers = beersLiveData.value ?: return
        val sortedBeers = currentBeers.sortedBy { it.howMany }
        repository.updateBeersLiveData(sortedBeers)
    }


    fun filterAlphabetical() {
        val currentBeers = beersLiveData.value ?: return
        val filteredBeers = currentBeers.filter { it.name != null }.sortedBy { it.name }
        repository.updateBeersLiveData(filteredBeers)
    }

    fun filterByVolume() {
        val currentBeers = beersLiveData.value ?: return
        val filteredBeers = currentBeers.filter { it.volume != null }.sortedBy { it.volume }
        repository.updateBeersLiveData(filteredBeers)
    }

    fun filterByHowMany() {
        val currentBeers = beersLiveData.value ?: return
        val filteredBeers = currentBeers.filter { it.howMany != null }.sortedBy { it.howMany }
        repository.updateBeersLiveData(filteredBeers)
    }


}
