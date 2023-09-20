package com.rodrigonovoa.rickandmortycharacters.api

import androidx.lifecycle.MutableLiveData
import com.rodrigonovoa.rickandmortycharacters.data.api.Result
import com.rodrigonovoa.rickandmortycharacters.data.model.CharacterRow

class RickAndMortyRepositoryImpl(
    private val service: RickAndMortyService
) : RickAndMortyRepository {

    var characters = MutableLiveData<List<CharacterRow>>()

    override fun getCharacters(page: Int) {
        val characterRowList = mapToCharacterRow(service.getCharacters(page).execute().body()?.results ?: listOf())
        characters.postValue(characterRowList)
    }

    private fun mapToCharacterRow(characters: List<Result>): List<CharacterRow> {
        return characters.map { CharacterRow(it.name, it.image) }
    }

}