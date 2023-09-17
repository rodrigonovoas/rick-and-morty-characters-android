package com.rodrigonovoa.rickandmortycharacters.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.rodrigonovoa.rickandmortycharacters.api.RickAndMortyClient
import com.rodrigonovoa.rickandmortycharacters.api.RickAndMortyRepositoryImpl
import com.rodrigonovoa.rickandmortycharacters.databinding.ActivityMainBinding
import com.rodrigonovoa.rickandmortycharacters.ui.adapters.CharactersRecyclerviewAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = MainActivityViewModel(RickAndMortyRepositoryImpl(RickAndMortyClient.instance))

        // Set an observer on the LiveData counter
        viewModel.characters.observe(this, Observer { characters ->
            val adapter = CharactersRecyclerviewAdapter(characters)
            binding.rcCharacters.layoutManager = LinearLayoutManager(this)
            binding.rcCharacters.adapter = adapter
        })
    }
}