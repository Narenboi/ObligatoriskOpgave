package com.example.obligatoriskopgave.repositories

import com.example.obligatoriskopgave.models.Beer
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface BeerService {
    @GET("beers/{user}")
    fun getAllBeers(@Path("user") user: String): Call<List<Beer>>
    @POST("beers")
    fun saveBeer(@Body beer: Beer): Call<List<Beer>>
    @PUT("beers/{id}")
    fun updateBeer(@Path("id") id: Int, @Body beer: Beer): Call<List<Beer>>
    @DELETE("beers/{id}")
    fun deleteBeer(@Path("id") id: Int): Call<List<Beer>>

}