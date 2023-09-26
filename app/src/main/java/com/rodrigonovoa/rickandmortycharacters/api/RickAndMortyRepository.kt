package com.rodrigonovoa.rickandmortycharacters.api

interface RickAndMortyRepository {
    fun getCharacters(page: Int)
    fun getCharactersByName(page:Int, name: String)

    fun getCharacterById(characterId: Int)
}