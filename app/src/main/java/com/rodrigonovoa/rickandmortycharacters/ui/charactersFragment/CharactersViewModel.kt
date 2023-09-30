package com.rodrigonovoa.rickandmortycharacters.ui.charactersFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigonovoa.rickandmortycharacters.api.RickAndMortyRepositoryImpl
import com.rodrigonovoa.rickandmortycharacters.data.api.CharacterResponse
import com.rodrigonovoa.rickandmortycharacters.data.api.ResultResponse
import com.rodrigonovoa.rickandmortycharacters.data.model.CharacterRow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharactersViewModel(private val repository: RickAndMortyRepositoryImpl): ViewModel() {

    private val _characters: MutableLiveData<List<CharacterRow>> = MutableLiveData()
    val characters: LiveData<List<CharacterRow>> get() = _characters

    private val _errorLoading: MutableLiveData<Boolean> = MutableLiveData()
    val errorLoading: LiveData<Boolean> get() = _errorLoading

    private var lastPage = 1
    var currentPage = 0

    init {
        loadMoreCharacters(1)
    }

    fun loadMoreCharacters(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCharacters(page).collect {
                if(it.isSuccess) {
                    processApiResult(it, page)
                } else {
                    _errorLoading.postValue(true)
                }
            }
        }
    }

    private fun mapToCharacterRow(characters: List<ResultResponse>): List<CharacterRow> {
        return characters.map { CharacterRow(it.id, it.name, it.image) }
    }

    fun getCharactersByName(page: Int, name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (page > lastPage) { return@launch }
            repository.getCharactersByName(page, name).collect {
                if(it.isSuccess) {
                    processApiResult(it, page)
                } else {
                    _errorLoading.postValue(true)
                }
            }
        }
    }

    fun processApiResult(apiResult: Result<CharacterResponse>, page: Int) {
        val charactersFromApi = apiResult.getOrNull()
        lastPage = charactersFromApi?.info?.pages ?: 1
        val mappedCharacters = mapToCharacterRow(charactersFromApi?.results ?: listOf())

        if (page == 1) {
            _characters.postValue(mappedCharacters)
        } else {
            val currentCharacterList = _characters.value?.toMutableList() ?: mutableListOf()
            currentCharacterList.addAll(mappedCharacters)
            _characters.postValue(currentCharacterList)
        }
    }

    fun clearPagesData() {
        currentPage = 0
        lastPage = 1
    }
}