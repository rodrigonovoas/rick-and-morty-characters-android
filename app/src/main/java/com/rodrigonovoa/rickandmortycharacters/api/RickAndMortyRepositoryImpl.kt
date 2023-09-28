package com.rodrigonovoa.rickandmortycharacters.api

import com.rodrigonovoa.rickandmortycharacters.data.api.CharacterResponse
import com.rodrigonovoa.rickandmortycharacters.data.api.ResultResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RickAndMortyRepositoryImpl(
    private val service: RickAndMortyService
) : RickAndMortyRepository {

    override fun getCharacters(page: Int): Flow<Result<CharacterResponse>> = flow {
        try {
            val response = service.getCharacters(page)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Result.success(it))
                }
            } else {
                emit(Result.failure(Throwable(response.message())))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override fun getCharactersByName(page: Int, name: String): Flow<Result<CharacterResponse>> = flow {
        try {
            val response = service.getCharactersByName(page, name)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Result.success(it))
                }
            } else {
                emit(Result.failure(Throwable(response.message())))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override fun getCharacterById(characterId: Int): Flow<Result<ResultResponse>> = flow {
        try {
            val response = service.getCharacterById(characterId)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Result.success(it))
                }
            } else {
                emit(Result.failure(Throwable(response.message())))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}