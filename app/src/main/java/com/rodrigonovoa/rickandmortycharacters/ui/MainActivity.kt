package com.rodrigonovoa.rickandmortycharacters.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rodrigonovoa.rickandmortycharacters.data.model.CharacterRow
import com.rodrigonovoa.rickandmortycharacters.databinding.ActivityMainBinding
import com.rodrigonovoa.rickandmortycharacters.ui.adapters.CharactersRecyclerviewAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainActivityViewModel by viewModel()
    private var currentPage = 0
    private var characterListLoading = false
    private var enteredName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observers()
        viewListeners()
    }

    private fun viewListeners() {
        scrollListener()
        searchBarListener()
    }

    private fun searchBarListener() {
        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                enteredName = s.toString()
                mainViewModel.getCharactersByName(1, s.toString())
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

    private fun observers() {
        mainViewModel.characters.observe(this, Observer { characters ->
            characterListLoading = false

            with(binding.rcCharacters) {
                if (adapter == null) {
                    initializeRecyclerView(characters)
                } else {
                    (adapter as? CharactersRecyclerviewAdapter)?.setDataSet(characters)
                }
            }
        })
    }

    private fun initializeRecyclerView(characters: List<CharacterRow>) {
        with(binding.rcCharacters) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = CharactersRecyclerviewAdapter(characters)
        }
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

        if(!enteredName.isNullOrEmpty()) {
            mainViewModel.getCharactersByName(currentPage, enteredName)
        } else {
            mainViewModel.loadMoreCharacters(currentPage)
        }
    }

}