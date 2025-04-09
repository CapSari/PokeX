package com.dve.sari.minipokedex.data.api

import com.dve.sari.minipokedex.data.model.PokemonDetailsResponse
import com.dve.sari.minipokedex.data.model.PokemonsResponse
import com.dve.sari.minipokedex.data.model.SpeciesResponse
import com.slack.eithernet.ApiResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MiniPokedexApi{
    @GET("pokemon")
    suspend fun getAllPokemons(
        @Query("limit") limit: String = "100",
        @Query("offset") offset: String = "0"
    ) : ApiResult<PokemonsResponse, Any>

    @GET("pokemon/{id}")
    suspend fun getPokemonDetails(
        @Path("id") id: String
    ) : ApiResult<PokemonDetailsResponse, Any>

    @GET("pokemon-species/{id}")
    suspend fun getSpeciesDetails(
        @Path("id") speciesId: String
    ): ApiResult<SpeciesResponse, Any>
}