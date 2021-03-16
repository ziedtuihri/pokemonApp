package com.example.pokemon

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon.adapter.PokemonAdapter
import com.example.pokemon.data.PokemonDatabase
import com.example.pokemon.data.PokemonEntity
import com.example.pokemon.databinding.ActivityMainBinding
import com.example.pokemon.model.Pokemon
import com.example.pokemon.networking.ApiService
import com.example.pokemon.networking.NetworkClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    lateinit var adapterPokemon: PokemonAdapter
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        adapterPokemon = PokemonAdapter(this)
        setContentView(binding.root)
        recyclerView = binding.recycler

        val service = NetworkClient().getRetrofit().create(ApiService::class.java)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapterPokemon
        service.getAllPokemons().enqueue(object : Callback<List<Pokemon>> {
            override fun onResponse(call: Call<List<Pokemon>>, response: Response<List<Pokemon>>) {
                if (response.isSuccessful) {
                    Log.e(TAG, "onResponse: ${response.body()?.get(0)}")
                    val listPokemon: List<Pokemon>? = response.body()
                    val database = PokemonDatabase.getInstance(this@MainActivity)
                    val dao = database.getPokemonDao()

                    GlobalScope.launch(Dispatchers.Main) {
                        dao.insertAll(
                            listPokemon!!.map {
                                PokemonEntity(
                                    name = it.name ,
                                    attack = it.attack,
                                    image = it.imageurl,

                                )
                            }
                        )
                        val list = dao.getAll().map { Pokemon(name = it.name, imageurl = it.image, attack = it.attack) }
                        adapterPokemon.setPokemonListItems(list)
                        binding.recycler.apply {
                            layoutManager = LinearLayoutManager(this@MainActivity)
                            adapter = adapterPokemon
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<Pokemon>>, t: Throwable) {
                Log.e(TAG, "onFailure: ", t)
                Toast.makeText(this@MainActivity, "ERROR", Toast.LENGTH_LONG).show()
            }
        })
    }
}