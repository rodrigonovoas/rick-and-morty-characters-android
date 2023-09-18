package com.rodrigonovoa.rickandmortycharacters.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.rodrigonovoa.rickandmortycharacters.databinding.ActivityMainBinding
import com.rodrigonovoa.rickandmortycharacters.ui.adapters.CharactersRecyclerviewAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observers()
    }

    private fun observers() {
        mainViewModel.characters.observe(this, Observer { characters ->
            val adapter = CharactersRecyclerviewAdapter(characters)
            binding.rcCharacters.layoutManager = LinearLayoutManager(this)
            binding.rcCharacters.adapter = adapter
        })
    }
}