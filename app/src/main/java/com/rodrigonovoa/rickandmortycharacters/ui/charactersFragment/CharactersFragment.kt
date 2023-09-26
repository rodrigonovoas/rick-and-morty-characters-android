package com.rodrigonovoa.rickandmortycharacters.ui.charactersFragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rodrigonovoa.rickandmortycharacters.R
import com.rodrigonovoa.rickandmortycharacters.data.model.CharacterRow
import com.rodrigonovoa.rickandmortycharacters.databinding.FragmentCharactersBinding
import com.rodrigonovoa.rickandmortycharacters.ui.adapters.CharactersRecyclerviewAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel


class CharactersFragment : Fragment(), CharactersRecyclerviewAdapter.ItemClickListener {

    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: CharactersViewModel by viewModel()
    private var currentPage = 0
    private var characterListLoading = false
    private var enteredName = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        mainViewModel.characters.observe(requireActivity(), Observer { characters ->
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
            layoutManager = GridLayoutManager(requireContext(), 4)
            adapter = CharactersRecyclerviewAdapter(characters, this@CharactersFragment)
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

    override fun onItemClicked(characterId: Int) {
        val bundle = Bundle()
        bundle.putInt("characterIdBundle", characterId)
        Navigation.findNavController(requireView()).navigate(R.id.action_nav_to_detail_fragment, bundle)
    }
}