package com.example.obligatoriskopgave.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.obligatoriskopgave.repositories.BeerRepository

class BeerViewModel : ViewModel()  {

    private val repository = BeerRepository()
    val beersLiveData: LiveData<List<Beer>> = repository.beersLiveData
    val errorMessageLiveData: LiveData<String> = repository.errorMessageLiveData
    val updateMessageLiveData: LiveData<String> = repository.updateMessageLiveData
    val reloadingLiveData: LiveData<Boolean> = repository.reloadingLiveData

    init {
        reload()
    }

    fun reload() {
        repository.getBeers()
    }

    operator fun get(index: Int): Beer? {
        return beersLiveData.value?.get(index)
    }

   /* fun add(beer: Beer) {
        repository.add(beer)
    }

    fun delete(id: Int) {
        repository.delete(id)
    }

    fun update(beer: Beer) {
        repository.update(beer)
    }*/

}