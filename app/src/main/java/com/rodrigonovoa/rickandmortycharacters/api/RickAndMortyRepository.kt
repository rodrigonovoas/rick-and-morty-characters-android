package com.rodrigonovoa.rickandmortycharacters.api

interface RickAndMortyRepository {
    fun getCharacters(page: Int)
}