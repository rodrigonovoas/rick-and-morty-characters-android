package com.rodrigonovoa.rickandmortycharacters.ui.charactersFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigonovoa.rickandmortycharacters.api.RickAndMortyRepositoryImpl
import com.rodrigonovoa.rickandmortycharacters.data.api.ResultResponse
import com.rodrigonovoa.rickandmortycharacters.data.model.CharacterRow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharactersViewModel(private val repository: RickAndMortyRepositoryImpl): ViewModel() {

    var characters: MutableLiveData<List<CharacterRow>> = MutableLiveData()

    init {
        loadMoreCharacters(1)
    }

    fun loadMoreCharacters(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCharacters(page).collect {
                if(it.isSuccess) {
                    var currentCharacterList = characters.value?.toMutableList() ?: mutableListOf()
                    val charactersFromApi = it.getOrNull()
                    val mappedCharacters = mapToCharacterRow(charactersFromApi?.results ?: listOf())
                    currentCharacterList?.addAll(mappedCharacters)
                    characters.postValue(currentCharacterList)
                } else {

                }
            }
        }
    }

    private fun mapToCharacterRow(characters: List<ResultResponse>): List<CharacterRow> {
        return characters.map { CharacterRow(it.id, it.name, it.image) }
    }

    fun getCharactersByName(page: Int, name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCharactersByName(page, name)
        }
    }
}