package com.rodrigonovoa.rickandmortycharacters.api

import com.rodrigonovoa.rickandmortycharacters.data.api.CharacterResponse
import com.rodrigonovoa.rickandmortycharacters.data.api.ResultResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyService {
    @GET("character/?")
    suspend fun getCharacters(@Query("page") currentPage: Int): Response<CharacterResponse>

    @GET("character/?")
    suspend fun getCharactersByName(@Query("page") currentPage: Int, @Query("name") characterName: String): Response<CharacterResponse>

    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") currentPage: Int): Response<ResultResponse>
}