package com.rodrigonovoa.rickandmortycharacters.ui.detailFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigonovoa.rickandmortycharacters.api.RickAndMortyRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: RickAndMortyRepositoryImpl): ViewModel() {
    var detail: MutableLiveData<com.rodrigonovoa.rickandmortycharacters.data.api.ResultResponse> = MutableLiveData()

    fun getCharacterById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCharacterById(id).collect {
                if(it.isSuccess) {
                    val character = it.getOrNull()
                    this@DetailViewModel.detail.postValue(character)
                } else {

                }
            }

        }
    }

}