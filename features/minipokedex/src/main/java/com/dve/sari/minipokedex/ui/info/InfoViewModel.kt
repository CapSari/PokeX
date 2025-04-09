package com.dve.sari.minipokedex.ui.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dve.sari.minipokedex.domain.model.Pokemon
import com.dve.sari.minipokedex.domain.usecase.GetPokemonDetails
import com.dve.sari.minipokedex.ui.home.ErrorType
import com.dve.sari.networking.di.IODispatcher
import com.dve.sari.networking.util.PokeXBaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class InfoViewModel @Inject constructor(
    private val getPokemonDetails: GetPokemonDetails,
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val getDetailsEvent = Channel<String>(Channel.CONFLATED)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uIState = getDetailsEvent
        .receiveAsFlow()
        .flatMapMerge {
            getPokemonDetails.execute(it)
        }.map { result ->
            when (result) {
                is PokeXBaseResult.Loading -> CharacterDetailsUIState.Loading
                is PokeXBaseResult.Failure -> {
                    when (val error = result.error) {
                        is GetPokemonDetails.Errors.HttpError -> {
                            CharacterDetailsUIState.Error(
                                errorType = ErrorType.HttpError(message = error.message)
                            )
                        }

                        GetPokemonDetails.Errors.NetworkError -> {
                            CharacterDetailsUIState.Error(errorType = ErrorType.NetworkError)
                        }

                        GetPokemonDetails.Errors.UnknownError -> {
                            CharacterDetailsUIState.Error(errorType = ErrorType.UnknownError)
                        }

                        else -> {}
                    }
                }

                is PokeXBaseResult.Success -> CharacterDetailsUIState.Success(result.data)
                else -> {}
            }
        }
        .flowOn(coroutineDispatcher)
        .stateIn(
            scope = viewModelScope,
            initialValue = CharacterDetailsUIState.Idle,
            started = SharingStarted.WhileSubscribed(5000L)
        )

    fun getDetails(id: String) {
        getDetailsEvent.trySend(id)
    }
}

sealed interface CharacterDetailsUIState {
    data object Idle : CharacterDetailsUIState
    data object Loading : CharacterDetailsUIState
    data class Success(val pokemon: Pokemon) : CharacterDetailsUIState
    data class Error(val errorType: ErrorType) : CharacterDetailsUIState
}
