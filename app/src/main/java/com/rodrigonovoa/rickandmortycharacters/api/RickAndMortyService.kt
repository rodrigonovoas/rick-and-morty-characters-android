package com.rodrigonovoa.rickandmortycharacters.api

import com.rodrigonovoa.rickandmortycharacters.data.api.CharacterResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyService {
    @GET("character/?")
    fun getCharacters(@Query("page") currentPage: Int): Call<CharacterResponse>

    @GET("character/?")
    fun getCharactersByName(@Query("page") currentPage: Int, @Query("name") characterName: String): Call<CharacterResponse>

    @GET("character/{id}")
    fun getCharacterById(@Path("id") currentPage: Int): Call<com.rodrigonovoa.rickandmortycharacters.data.api.Result>
}