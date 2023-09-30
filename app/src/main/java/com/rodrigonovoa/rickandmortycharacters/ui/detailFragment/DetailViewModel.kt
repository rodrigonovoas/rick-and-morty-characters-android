package com.rodrigonovoa.rickandmortycharacters.ui.detailFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigonovoa.rickandmortycharacters.api.RickAndMortyRepositoryImpl
import com.rodrigonovoa.rickandmortycharacters.data.api.ResultResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: RickAndMortyRepositoryImpl): ViewModel() {
    private val _detail: MutableLiveData<ResultResponse> = MutableLiveData()
    val detail: LiveData<ResultResponse> get() = _detail

    private val _errorLoading: MutableLiveData<Boolean> = MutableLiveData()
    val errorLoading: LiveData<Boolean> get() = _errorLoading

    fun getCharacterById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCharacterById(id).collect {
                if(it.isSuccess) {
                    val character = it.getOrNull()
                    this@DetailViewModel._detail.postValue(character)
                } else {
                    _errorLoading.postValue(true)
                }
            }

        }
    }

}