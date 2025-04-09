package com.dve.sari.minipokedex.domain.model

import com.dve.sari.minipokedex.data.model.PokemonDTO
import com.dve.sari.networking.util.createImageUrl
import com.pokamon.features.pokedex.domain.mapper.PokemonColor


data class Pokemon(
    val id: String,
    val name: String,
    val stats: List<Pair<String, Int>>,
    val imageUrl: String,
    val about: String,
    val height: String,
    val weight: String,
    val abilities: List<Pair<String, Boolean>>,
    val types: List<String>,
    val pokemonColor: PokemonColor,
)

data class PokemonListing(
    val id: String,
    val name: String,
    val imageUrl: String
)

fun List<PokemonDTO>.toCharacterListing() = map {
    val id = it.url.getIdFromUrl()
    PokemonListing(
        name = it.name,
        id = id,
        imageUrl = createImageUrl(id)
    )
}

fun String.getIdFromUrl(): String {
    val split = split("/").toMutableList().apply {
        removeIf { value ->
            value.isBlank()
        }
    }
    return split.last()
}