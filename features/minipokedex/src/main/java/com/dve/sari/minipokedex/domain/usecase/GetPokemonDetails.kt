package com.dve.sari.minipokedex.domain.usecase

import com.dve.sari.minipokedex.data.repository.MiniPokedexRepository
import com.dve.sari.minipokedex.domain.model.Pokemon
import com.dve.sari.minipokedex.ui.home.CharactersUIState
import com.dve.sari.minipokedex.ui.home.ErrorType
import com.dve.sari.networking.util.PokeXBaseResult
import com.dve.sari.minipokedex.domain.mapper.CharacterDetailsMapper
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

typealias PokemonDetailsResult = PokeXBaseResult<
        Pokemon, GetPokemonDetails.Errors>

class GetPokemonDetails @Inject constructor(
    private val repository: MiniPokedexRepository,
    private val characterDetailsMapper: CharacterDetailsMapper
) {
    sealed interface Errors {
        data object NetworkError : Errors
        data object UnknownError : Errors
        data class HttpError(
            val message: String
        ) : Errors
    }

    fun execute(id: String) = flow {
        emit(PokeXBaseResult.Loading())
        val details = repository.getCharacterDetails(id)
        val speciesDetails = repository.getSpeciesDetails(id)

        val result = characterDetailsMapper.mapResult(details, speciesDetails)
        emit(result)
    }.catch {
        it.printStackTrace()
        emit(PokeXBaseResult.Failure(Errors.UnknownError))
    }
}
