package com.rodrigonovoa.rickandmortycharacters.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rodrigonovoa.rickandmortycharacters.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewmodel = MainActivityViewModel()
        viewmodel.getCharactersFromApi()
    }
}