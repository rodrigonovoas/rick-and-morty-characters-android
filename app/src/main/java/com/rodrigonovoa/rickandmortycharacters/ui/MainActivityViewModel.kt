package com.rodrigonovoa.rickandmortycharacters.ui

import androidx.lifecycle.ViewModel
import com.rodrigonovoa.rickandmortycharacters.api.RickAndMortyClient
import com.rodrigonovoa.rickandmortycharacters.data.api.CharacterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel: ViewModel() {

    fun getCharactersFromApi() {
        RickAndMortyClient.instance.getCharacters().enqueue(object : Callback<CharacterResponse> {
            override fun onResponse(call: Call<CharacterResponse>, response: Response<CharacterResponse>) {
                if (response.isSuccessful) {
                    val character = response.body()
                    // Handle the successful response here
                } else {
                    // Handle the error response, e.g., show an error message
                }
            }

            override fun onFailure(call: Call<CharacterResponse>, t: Throwable) {
                // Handle the failure, e.g., show an error message or retry the request
            }
        })
    }

}