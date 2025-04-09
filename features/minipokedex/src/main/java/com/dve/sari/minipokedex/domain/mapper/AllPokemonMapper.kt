package com.dve.sari.minipokedex.domain.mapper

import com.dve.sari.minipokedex.data.model.PokemonsResponse
import com.dve.sari.minipokedex.domain.model.PokemonListing
import com.dve.sari.minipokedex.domain.model.toCharacterListing
import com.dve.sari.minipokedex.domain.usecase.GetPokemons
import com.dve.sari.networking.util.PokeXBaseResult
import com.slack.eithernet.ApiResult
import javax.inject.Inject

internal typealias GetPokemonsResult = PokeXBaseResult<List<PokemonListing>, GetPokemons.Errors>

interface AllPokemonMapper {
    fun mapResult(
        result: ApiResult<PokemonsResponse, Any>
    ): GetPokemonsResult
}

class AllPokemonMapperImpl @Inject internal constructor() : AllPokemonMapper {
    override fun mapResult(result: ApiResult<PokemonsResponse, Any>): GetPokemonsResult {
        return when (result) {
            is ApiResult.Success -> {
                val data = result.value.results
                val characters = data.toCharacterListing()
                PokeXBaseResult.Success(
                    data = characters
                )
            }

            is ApiResult.Failure.NetworkFailure -> {
                PokeXBaseResult.Failure(GetPokemons.Errors.NetworkError)
            }

            is ApiResult.Failure.UnknownFailure -> {
                result.error.printStackTrace()
                PokeXBaseResult.Failure(GetPokemons.Errors.UnknownError)
            }

            is ApiResult.Failure.HttpFailure -> {
                val message = result.error?.toString().orEmpty()
                PokeXBaseResult.Failure(
                    GetPokemons.Errors.HttpError(
                        message = message
                    )
                )
            }

            is ApiResult.Failure.ApiFailure -> {
                PokeXBaseResult.Failure(GetPokemons.Errors.UnknownError)
            }
        }
    }

}