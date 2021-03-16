package com.example.pokemon.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.pokemon.databinding.ItemLayoutBinding
import com.example.pokemon.model.Pokemon

class PokemonAdapter(private val context: Context) :
    RecyclerView.Adapter<PokemonAdapter.MyViewHolder>() {
    private var pokemonList: List<Pokemon> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val binding = ItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        holder.pokemonName.text = "name :" + pokemonList.get(position).name
        holder.pokemonAttack.text = "attack :" + pokemonList.get(position).attack.toString()
        Glide.with(context).load(pokemonList.get(position).imageurl)
            .apply(RequestOptions().centerCrop())
            .into(holder.image)
    }



    class MyViewHolder(binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        val pokemonName: TextView = binding.title
        val pokemonAttack: TextView = binding.attack
        val image: ImageView = binding.pokemonImage

    }

    fun setPokemonListItems(pokemonList: List<Pokemon>) {
        this.pokemonList = pokemonList;
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }
}