package com.dve.sari.minipokedex.data.repository

import com.dve.sari.minipokedex.data.api.MiniPokedexApi
import com.dve.sari.minipokedex.data.model.PokemonDetailsResponse
import com.dve.sari.minipokedex.data.model.PokemonsResponse
import com.dve.sari.minipokedex.data.model.SpeciesResponse
import com.slack.eithernet.ApiResult
import javax.inject.Inject


class MiniPokedexRepositoryImpl @Inject constructor(
    private val miniPokedexApi: MiniPokedexApi
) : MiniPokedexRepository {
    override suspend fun getAllCharacters(): ApiResult<PokemonsResponse, Any> {
        return miniPokedexApi.getAllPokemons()
    }

    override suspend fun getCharacterDetails(id: String): ApiResult<PokemonDetailsResponse, Any> {
        return miniPokedexApi.getPokemonDetails(id)
    }

    override suspend fun getSpeciesDetails(id: String): ApiResult<SpeciesResponse, Any> {
        return miniPokedexApi.getSpeciesDetails(id)
    }
}