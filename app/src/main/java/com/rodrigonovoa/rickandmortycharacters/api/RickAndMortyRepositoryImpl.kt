package com.rodrigonovoa.rickandmortycharacters.api

import androidx.lifecycle.MutableLiveData
import com.rodrigonovoa.rickandmortycharacters.data.api.Result
import com.rodrigonovoa.rickandmortycharacters.data.model.CharacterRow

class RickAndMortyRepositoryImpl(
    private val service: RickAndMortyService
) : RickAndMortyRepository {

    var characters = MutableLiveData<List<CharacterRow>>()

    override fun getCharacters(page: Int) {
        var currentCharacterList = characters.value?.toMutableList() ?: mutableListOf()
        val characterRowList = mapToCharacterRow(service.getCharacters(page).execute().body()?.results ?: listOf())
        currentCharacterList?.addAll(characterRowList)
        characters.postValue(currentCharacterList)
    }

    override fun getCharactersByName(page: Int, name: String) {
        var currentCharacterList = characters.value?.toMutableList() ?: mutableListOf()
        val characterRowList = mapToCharacterRow(service.getCharactersByName(page, name).execute().body()?.results ?: listOf())
        currentCharacterList?.addAll(characterRowList)
        if (page == 1) {
            characters.postValue(characterRowList)
        } else {
            characters.postValue(currentCharacterList)
        }
    }

    private fun mapToCharacterRow(characters: List<Result>): List<CharacterRow> {
        return characters.map { CharacterRow(it.name, it.image) }
    }

}