package com.dve.sari.minipokedex.data.model

import com.dve.sari.minipokedex.domain.model.Pokemon
import com.dve.sari.minipokedex.domain.model.getIdFromUrl
import com.dve.sari.minipokedex.domain.model.toCharacterListing
import com.pokamon.features.pokedex.domain.mapper.PokemonColor
import org.junit.Assert.assertEquals
import org.junit.Test

class MiniPokedexDTOTest {

    @Test
    fun `getIdFromUrl should extract the correct ID`() {
        val url = "https://pokeapi.co/api/v2/pokemon/25/"
        val expectedId = "25"
        val actualId = url.getIdFromUrl()

        assertEquals(expectedId, actualId)
    }

    @Test
    fun `toCharacterListing should map DTOs to PokemonListings correctly`() {
        val dtos = listOf(
            PokemonDTO(name = "pikachu", url = "https://pokeapi.co/api/v2/pokemon/25/"),
            PokemonDTO(name = "bulbasaur", url = "https://pokeapi.co/api/v2/pokemon/1/")
        )

        val listings = dtos.toCharacterListing()

        assertEquals(2, listings.size)
        assertEquals("pikachu", listings[0].name)
        assertEquals("25", listings[0].id)
        assertEquals("bulbasaur", listings[1].name)
        assertEquals("1", listings[1].id)
    }

    @Test
    fun `Pokemon data class should hold all fields correctly`() {
        val pokemon = Pokemon(
            id = "1",
            name = "bulbasaur",
            stats = listOf("hp" to 45, "attack" to 49),
            imageUrl = "https://example.com/image.png",
            about = "A strange seed was planted on its back at birth.",
            height = "0.7 m",
            weight = "6.9 kg",
            abilities = listOf("overgrow" to false, "chlorophyll" to true),
            types = listOf("grass", "poison"),
            pokemonColor = PokemonColor.Yellow
        )

        assertEquals("1", pokemon.id)
        assertEquals("bulbasaur", pokemon.name)
        assertEquals("grass", pokemon.types[0])
        assertEquals("poison", pokemon.types[1])
    }
}
