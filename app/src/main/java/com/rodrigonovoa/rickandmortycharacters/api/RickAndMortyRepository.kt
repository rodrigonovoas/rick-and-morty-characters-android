package com.rodrigonovoa.rickandmortycharacters.api

import com.rodrigonovoa.rickandmortycharacters.data.api.CharacterResponse
import com.rodrigonovoa.rickandmortycharacters.data.api.ResultResponse
import kotlinx.coroutines.flow.Flow

interface RickAndMortyRepository {
    fun getCharacters(page: Int): Flow<Result<CharacterResponse>>
    fun getCharactersByName(page:Int, name: String): Flow<Result<CharacterResponse>>

    fun getCharacterById(characterId: Int): Flow<Result<ResultResponse>>
}