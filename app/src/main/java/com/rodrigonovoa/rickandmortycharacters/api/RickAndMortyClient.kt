package com.rodrigonovoa.rickandmortycharacters.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RickAndMortyClient {
    private const val BASE_URL = "https://rickandmortyapi.com/api/"

    private val okHttpClient = OkHttpClient.Builder()
        .build()

    val instance: RickAndMortyService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        retrofit.create(RickAndMortyService::class.java)
    }
}