package com.rodrigonovoa.rickandmortycharacters.ui.charactersFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rodrigonovoa.rickandmortycharacters.R
import com.rodrigonovoa.rickandmortycharacters.data.model.CharacterRow
import com.rodrigonovoa.rickandmortycharacters.databinding.FragmentCharactersBinding
import com.rodrigonovoa.rickandmortycharacters.ui.MainActivity
import com.rodrigonovoa.rickandmortycharacters.ui.adapters.CharactersRecyclerviewAdapter
import com.rodrigonovoa.rickandmortycharacters.utils.DialogUtils
import org.koin.androidx.viewmodel.ext.android.viewModel


class CharactersFragment : Fragment(), CharactersRecyclerviewAdapter.ItemClickListener {
    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: CharactersViewModel by viewModel()
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
        changeToolbarName()
    }

    private fun changeToolbarName() {
        (activity as? MainActivity)?.changeToolbarName("Characters")
    }

    private fun viewListeners() {
        scrollListener()
        searchBarListener()
    }

    private fun searchBarListener() {
        binding.edtSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                mainViewModel.clearPagesData()
                val enteredText = binding.edtSearch.text.toString()
                enteredName = enteredText
                if (enteredText.isNotEmpty()) {
                    // if not empty, searches the entered character
                    mainViewModel.getCharactersByName(1, enteredText)
                } else {
                    // if empty, searches the general list of characters
                    mainViewModel.loadMoreCharacters(1)
                }
                true
            } else {
                false
            }
        }
    }

    private fun observers() {
        mainViewModel.characters.observe(requireActivity(), Observer { characters ->
            characterListLoading = false
            binding.tvCharacterNotFound.visibility = View.GONE

            with(binding.rcCharacters) {
                if (adapter == null) {
                    initializeRecyclerView(characters)
                } else {
                    // updated the data retrieved from API
                    (adapter as? CharactersRecyclerviewAdapter)?.setDataSet(characters)
                }
            }
        })

        mainViewModel.errorLoading.observe(requireActivity(), Observer { value ->
            if(value) {
                DialogUtils.showErrorDialog(requireContext())
            }
        })

        mainViewModel.noCharacteresFound.observe(requireActivity(), Observer { value ->
            if(value) {
                // if the introduced data is not found, the recyclerview is emptied
                // and the informative label is visible
                (binding.rcCharacters.adapter as? CharactersRecyclerviewAdapter)?.setDataSet(listOf())
                binding.tvCharacterNotFound.visibility = View.VISIBLE
            }
        })
    }

    private fun initializeRecyclerView(characters: List<CharacterRow>) {
        with(binding.rcCharacters) {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = CharactersRecyclerviewAdapter(characters, this@CharactersFragment)
        }
    }

    private fun scrollListener() {
        val visibleThreshold = 5 // limit to start loading more characters
        mainViewModel.currentPage = 1

        binding.rcCharacters.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalCharactersCount = layoutManager.itemCount
                val lastVisibleCharacter = layoutManager.findLastVisibleItemPosition() // number of the last visible char.

                if (!characterListLoading && totalCharactersCount <= lastVisibleCharacter + visibleThreshold) {
                    mainViewModel.currentPage++
                    loadNextPageAfterReachingEnd()
                }
            }
        })
    }

    private fun loadNextPageAfterReachingEnd() {
        characterListLoading = true

        // differences between loading more characteres from the normal feed or after searching one
        if(!enteredName.isNullOrEmpty()) {
            mainViewModel.getCharactersByName(mainViewModel.currentPage, enteredName)
        } else {
            mainViewModel.loadMoreCharacters(mainViewModel.currentPage)
        }
    }

    override fun onItemClicked(characterId: Int) {
        val bundle = Bundle()
        bundle.putInt("characterIdBundle", characterId)
        Navigation.findNavController(requireView()).navigate(R.id.action_nav_to_detail_fragment, bundle)
    }
}