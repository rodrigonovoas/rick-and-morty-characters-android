package com.rodrigonovoa.rickandmortycharacters.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rodrigonovoa.rickandmortycharacters.databinding.ActivityMainBinding
import com.rodrigonovoa.rickandmortycharacters.ui.adapters.CharactersRecyclerviewAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainActivityViewModel by viewModel()
    private var currentPage = 0
    private var characterListLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observers()
        scrollListener()
    }

    private fun observers() {
        mainViewModel.characters.observe(this, Observer { characters ->
            characterListLoading = false
            val adapter = CharactersRecyclerviewAdapter(characters)
            binding.rcCharacters.layoutManager = LinearLayoutManager(this)
            binding.rcCharacters.adapter = adapter
        })
    }

    private fun scrollListener() {
        val visibleThreshold = 5 // limit to start loading more characters
        characterListLoading = false
        currentPage = 1

        binding.rcCharacters.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalCharactersCount = layoutManager.itemCount
                val lastVisibleCharacter = layoutManager.findLastVisibleItemPosition() // number of the last visible char.

                if (!characterListLoading && totalCharactersCount <= lastVisibleCharacter + visibleThreshold) {
                    currentPage++
                    loadNextPageAfterReachingEnd()
                }
            }
        })
    }

    private fun loadNextPageAfterReachingEnd() {
        characterListLoading = true
        mainViewModel.loadMoreCharacters(currentPage)
    }

}