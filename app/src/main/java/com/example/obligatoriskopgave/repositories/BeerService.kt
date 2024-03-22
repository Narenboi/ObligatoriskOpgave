package com.example.obligatoriskopgave.repositories

import com.example.obligatoriskopgave.models.Beer
import retrofit2.Call
import retrofit2.http.GET

interface BeerService {

    @GET("beers")
    fun getAllBeers(): Call<List<Beer>>
}