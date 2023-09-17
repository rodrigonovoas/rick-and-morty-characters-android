package com.rodrigonovoa.rickandmortycharacters.data.api

data class CharacterResponse(
    val info: Info,
    val results: List<Result>
)