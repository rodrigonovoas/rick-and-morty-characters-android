package com.rodrigonovoa.rickandmortycharacters.api

import com.rodrigonovoa.rickandmortycharacters.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RickAndMortyClient {
    private const val BASE_URL = "https://rickandmortyapi.com/api/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private var okHttpClient = OkHttpClient.Builder()
        .build()

    val instance: RickAndMortyService by lazy {

        if (BuildConfig.DEBUG) {
            okHttpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
        }

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        retrofit.create(RickAndMortyService::class.java)
    }
}