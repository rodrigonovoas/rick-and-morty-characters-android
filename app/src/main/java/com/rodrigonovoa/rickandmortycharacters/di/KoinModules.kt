package com.rodrigonovoa.rickandmortycharacters.di


import com.rodrigonovoa.rickandmortycharacters.api.RickAndMortyClient
import com.rodrigonovoa.rickandmortycharacters.api.RickAndMortyRepositoryImpl
import com.rodrigonovoa.rickandmortycharacters.api.RickAndMortyService
import com.rodrigonovoa.rickandmortycharacters.ui.charactersFragment.CharactersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // Single instances
    single<RickAndMortyService> { RickAndMortyClient.instance }
    single<RickAndMortyRepositoryImpl> { RickAndMortyRepositoryImpl(get()) }

    // ViewModel
    viewModel { CharactersViewModel(get()) }
}