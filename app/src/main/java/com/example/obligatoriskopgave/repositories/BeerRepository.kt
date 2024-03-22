package com.example.obligatoriskopgave.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.obligatoriskopgave.models.Beer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BeerRepository {

    private val baseUrl = "https://anbo-restbeer.azurewebsites.net/api/"
    // the specific (collection) part of the URL is on the individual methods in the interface BookstoreService

    private val beerService: BeerService
    val beersLiveData: MutableLiveData<List<Beer>> = MutableLiveData<List<Beer>>()
    val errorMessageLiveData: MutableLiveData<String> = MutableLiveData()
    val updateMessageLiveData: MutableLiveData<String> = MutableLiveData()
    val reloadingLiveData: MutableLiveData<Boolean> = MutableLiveData()

    init {
        //val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        val build: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create()) // GSON
            //.addConverterFactory(KotlinJsonAdapterFactory)
            //.addConverterFactory(MoshiConverterFactory.create(moshi)) // Moshi, added to Gradle dependencies
            .build()
        beerService = build.create(BeerService::class.java)
        getBeers()
    }

    fun getBeers() {
        reloadingLiveData.value = true
        beerService.getAllBeers().enqueue(object : Callback<List<Beer>> {
            override fun onResponse(call: Call<List<Beer>>, response: Response<List<Beer>>) {
                reloadingLiveData.value = false
                if (response.isSuccessful) {
                    //Log.d("APPLE", response.body().toString())
                    val b: List<Beer>? = response.body()
                    beersLiveData.postValue(b!!)
                    errorMessageLiveData.postValue("")
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("APPLE", message)
                }
            }

            override fun onFailure(call: Call<List<Beer>>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("APPLE", t.message!!)
            }


        })
    }
}