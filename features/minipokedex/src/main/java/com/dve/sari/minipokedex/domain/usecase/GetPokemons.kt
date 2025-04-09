package com.dve.sari.minipokedex.domain.usecase

import com.dve.sari.minipokedex.data.repository.MiniPokedexRepository
import com.dve.sari.minipokedex.ui.home.CharactersUIState
import com.dve.sari.minipokedex.domain.mapper.AllPokemonMapper
import com.dve.sari.networking.util.PokeXBaseResult
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetPokemons @Inject constructor(
    private val pokedexRepository: MiniPokedexRepository,
    private val mapper: AllPokemonMapper
) {
    sealed interface Errors {
        data object NetworkError : Errors, GetPokemonDetails.Errors
        data object UnknownError : Errors, GetPokemonDetails.Errors
        data class HttpError(
            val message: String
        ) : Errors
    }

    fun execute() = flow {
        emit(PokeXBaseResult.Loading())
        val response = pokedexRepository.getAllCharacters()
        emit(mapper.mapResult(response))
    }.catch {
        it.printStackTrace()
        emit(PokeXBaseResult.Failure(Errors.UnknownError))
    }
}