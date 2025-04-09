package com.dve.sari.networking.util

sealed class PokeXBaseResult<T : Any, E : Any> {
    class Loading<T : Any, E : Any> : PokeXBaseResult<T, E>()
    class Failure<T : Any, E : Any>(val error: E) : PokeXBaseResult<T, E>()
    class Success<T : Any, E : Any>(val data: T) : PokeXBaseResult<T, E>()
}