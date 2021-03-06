package com.example.pokemon.networking

import com.example.pokemon.model.Pokemon
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("scitbiz/0bfdd96d3ab9ee20c2e572e47c6834c7/raw/pokemons.json")
    fun getAllPokemons(): Call<List<Pokemon>>

    @POST("user")
    fun createPokemon(@Body pokemon: Pokemon)

    // deletePokemon(1, "tester") -> pokemon/1/tester
    @GET("pokemon/{id}/{name}")
    fun deletePokemon(@Query("id") id: Int, @Query("name") name: String)

}