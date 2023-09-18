package com.rodrigonovoa.rickandmortycharacters

import android.app.Application
import com.rodrigonovoa.rickandmortycharacters.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class RickAndMortyCharactersApp: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@RickAndMortyCharactersApp)
            modules(appModule)
        }
    }
}