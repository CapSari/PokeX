/*
package com.dve.sari.minipokedex.data.repository

import com.dve.sari.minipokedex.data.api.MiniPokedexApi
import com.dve.sari.minipokedex.data.model.Ability
import com.dve.sari.minipokedex.data.model.AbilityDetails
import com.dve.sari.minipokedex.data.model.PokemonDTO
import com.dve.sari.minipokedex.data.model.PokemonDetailsResponse
import com.dve.sari.minipokedex.data.model.PokemonsResponse
import com.dve.sari.minipokedex.data.model.SpeciesDTO
import com.dve.sari.minipokedex.data.model.SpeciesResponse
import com.dve.sari.minipokedex.data.model.Sprites
import com.dve.sari.minipokedex.data.model.StatDTO
import com.dve.sari.minipokedex.data.model.StatDetails
import com.dve.sari.minipokedex.data.model.Type
import com.dve.sari.minipokedex.data.model.TypeHolder
import com.slack.eithernet.ApiResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals

@ExperimentalCoroutinesApi
class MiniPokedexRepositoryImplTest {

    private lateinit var repository: MiniPokedexRepositoryImpl
    private val mockApi = mockk<MiniPokedexApi>()

    @Before
    fun setup() {
        repository = MiniPokedexRepositoryImpl(mockApi)
    }

    @Test
    fun `test getAllCharacters returns success result`() = runTest {
        // Mocking API response with a list of PokemonDTO objects and a count value
        val mockPokemonsResponse = PokemonsResponse(
            count = 3,  // Assuming the count is 3 for this example
            results = listOf(
                PokemonDTO(name = "Bulbasaur", url = "url_bulbasaur"),
                PokemonDTO(name = "Charmander", url = "url_charmander"),
                PokemonDTO(name = "Squirtle", url = "url_squirtle")
            )
        )

        // Mocking the API response to return the mock response
        coEvery { mockApi.getAllPokemons() } returns ApiResult.Success()

        // Calling the repository method
        val result = repository.getAllCharacters()

        // Assertions
        assertTrue(result is ApiResult.Success)
        assertEquals(mockPokemonsResponse, (result as ApiResult.Success).value)
    }


    @Test
    fun `test getCharacterDetails returns success result`() = runTest {
        // Mocking AbilityDetails and Ability objects
        val mockAbilityDetails = AbilityDetails(name = "Overgrow", url = "https://pokeapi.co/api/v2/ability/65/")
        val mockAbility = Ability(ability = mockAbilityDetails, isHidden = false, slot = 1)

        // Mocking StatDetails and StatDTO objects
        val mockStatDetailsHP = StatDetails(name = "HP", url = "https://pokeapi.co/api/v2/stat/1/")
        val mockStatDTO = StatDTO(baseStat = 45, effort = 0, stat = mockStatDetailsHP)
        val mockStatDTOAttack = StatDTO(baseStat = 49, effort = 0, stat = StatDetails(name = "Attack", url = "https://pokeapi.co/api/v2/stat/2/"))
        val mockStatDTODefense = StatDTO(baseStat = 49, effort = 0, stat = StatDetails(name = "Defense", url = "https://pokeapi.co/api/v2/stat/3/"))

        // Mocking other necessary fields
        val mockSpecies = SpeciesDTO(name = "Bulbasaur", url = "https://pokeapi.co/api/v2/pokemon-species/1/")
        val mockSprites = Sprites(frontDefault = "front_url", backDefault = "back_url")
        val mockType = Type("name", "url")
        val mockTypeHolder = TypeHolder(type = mockType)

        // Mocking PokemonDetailsResponse with all required fields
        val mockPokemonDetailsResponse = PokemonDetailsResponse(
            abilities = listOf(mockAbility),
            baseExperience = 64,  // Example base experience
            forms = listOf(),  // Example empty list for forms
            height = 7,  // Example height in decimeters
            heldItems = emptyList(),  // Example empty list
            id = 1,  // Example ID
            isDefault = true,  // Example boolean
            locationAreaEncounters = "https://pokeapi.co/api/v2/pokemon/1/encounters",  // Example URL
            name = "Bulbasaur",
            order = 1,  // Example order
            species = mockSpecies,
            sprites = mockSprites,
            stats = listOf(mockStatDTO, mockStatDTOAttack, mockStatDTODefense),
            weight = 69,  // Example weight in hectograms
            types = listOf(mockTypeHolder)  // Example types list
        )

        coEvery { mockApi.getPokemonDetails("bulbasaur") } returns ApiResult.Success(mockPokemonDetailsResponse)

        // Calling the repository method
        val result = repository.getCharacterDetails("bulbasaur")

        // Assertions
        assertTrue(result is ApiResult.Success)
        assertEquals(mockPokemonDetailsResponse, (result as ApiResult.Success).value)
    }



    @Test
    fun `test getSpeciesDetails returns success result`() = runTest {
        // Mocking other necessary fields for SpeciesResponse
        val mockSpeciesDTO = SpeciesDTO(
            name = "Bulbasaur",
            url = "https://pokeapi.co/api/v2/pokemon-species/1/"
        )

        val mockSpeciesResponse = SpeciesResponse(
            baseHappiness = 70, // Example value
            captureRate = 45,   // Example value
            color = "green",    // Example value
            evolutionChain = "https://pokeapi.co/api/v2/evolution-chain/1/", // Example URL
            flavorTextEntries = listOf("Bulbasaur is a grass/poison type Pokemon."), // Example flavor text
            formsSwitchable = false, // Example value
            genderRate = 1,  // Example value
            generation = 1,  // Example value
            growthRate = "medium", // Example value
            habitat = "grassland", // Example value
            hasGenderDifferences = false, // Example value
            hatchCounter = 20, // Example value
            id = 1, // Example value
            isBaby = false, // Example value
            isLegendary = false, // Example value
            isMythical = false, // Example value
            names = listOf(), // Example list
            order = 1, // Example value
            palParkEncounters = listOf(), // Example list
            pokedexNumbers = listOf(), // Example list
            shape = "quadruped", // Example value
            varieties = listOf(), // Example list
            species = mockSpeciesDTO // Mock species DTO
        )

        // Mocking the API call
        coEvery { mockApi.getSpeciesDetails("bulbasaur") } returns ApiResult.Success(mockSpeciesResponse)

        // Calling the repository method
        val result = repository.getSpeciesDetails("bulbasaur")

        // Assertions
        assertTrue(result is ApiResult.Success)
        assertEquals(mockSpeciesResponse, (result as ApiResult.Success).value)
    }


    @Test
    fun `test getCharacterDetails returns error result when API call fails`() = runTest {
        // Mocking API error response
        coEvery { mockApi.getPokemonDetails("nonexistent") } returns ApiResult.Failure(Any())

        // Calling the repository method
        val result = repository.getCharacterDetails("nonexistent")

        // Assertions
        assertTrue(result is ApiResult.Failure)
    }

    @Test
    fun `test getAllCharacters handles empty list correctly`() = runTest {
        // Mocking API response with an empty list
        val mockPokemonsResponse = PokemonsResponse(results = emptyList())
        coEvery { mockApi.getAllPokemons() } returns ApiResult.Success(mockPokemonsResponse)

        // Calling the repository method
        val result = repository.getAllCharacters()

        // Assertions
        assertTrue(result is ApiResult.Success)
        assertTrue((result as ApiResult.Success).value.results.isEmpty())
    }
}
*/
