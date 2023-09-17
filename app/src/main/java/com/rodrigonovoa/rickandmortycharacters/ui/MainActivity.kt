package com.rodrigonovoa.rickandmortycharacters.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.rodrigonovoa.rickandmortycharacters.databinding.ActivityMainBinding
import com.rodrigonovoa.rickandmortycharacters.ui.adapters.CharactersRecyclerviewAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewmodel = MainActivityViewModel()
        viewmodel.getCharactersFromApi()

        // Set an observer on the LiveData counter
        viewmodel.characters.observe(this, Observer { characters ->
            val adapter = CharactersRecyclerviewAdapter(characters.results)
            binding.rcCharacters.layoutManager = LinearLayoutManager(this)
            binding.rcCharacters.adapter = adapter
        })
    }
}