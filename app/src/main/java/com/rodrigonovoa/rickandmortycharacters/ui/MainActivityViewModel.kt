package com.rodrigonovoa.rickandmortycharacters.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigonovoa.rickandmortycharacters.api.RickAndMortyRepositoryImpl
import com.rodrigonovoa.rickandmortycharacters.data.model.CharacterRow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(repository: RickAndMortyRepositoryImpl): ViewModel() {

    var characters: MutableLiveData<List<CharacterRow>> = MutableLiveData()

    init {
        this.characters = repository.characters

        viewModelScope.launch(Dispatchers.IO) {
            repository.getCharacters()
        }
    }

}