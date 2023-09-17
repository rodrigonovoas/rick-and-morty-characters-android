package com.rodrigonovoa.rickandmortycharacters.api

import com.rodrigonovoa.rickandmortycharacters.data.api.CharacterResponse
import retrofit2.Call
import retrofit2.http.GET

interface RickAndMortyService {
    @GET("character")
    fun getCharacters(): Call<CharacterResponse>
}