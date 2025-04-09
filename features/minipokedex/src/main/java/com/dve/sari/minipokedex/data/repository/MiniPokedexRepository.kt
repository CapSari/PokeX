package com.dve.sari.minipokedex.data.repository

import com.dve.sari.minipokedex.data.model.PokemonDetailsResponse
import com.dve.sari.minipokedex.data.model.PokemonsResponse
import com.dve.sari.minipokedex.data.model.SpeciesResponse
import com.slack.eithernet.ApiResult


interface MiniPokedexRepository {
    suspend fun getAllCharacters(): ApiResult<PokemonsResponse, Any>
    suspend fun getCharacterDetails(id: String): ApiResult<PokemonDetailsResponse, Any>
    suspend fun getSpeciesDetails(id: String): ApiResult<SpeciesResponse, Any>
}