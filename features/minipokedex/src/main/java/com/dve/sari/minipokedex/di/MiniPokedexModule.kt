package com.dve.sari.minipokedex.di

import com.dve.sari.minipokedex.data.api.MiniPokedexApi
import com.dve.sari.minipokedex.data.repository.MiniPokedexRepository
import com.dve.sari.minipokedex.data.repository.MiniPokedexRepositoryImpl
import com.dve.sari.minipokedex.domain.mapper.AllPokemonMapper
import com.dve.sari.minipokedex.domain.mapper.AllPokemonMapperImpl
import com.dve.sari.minipokedex.domain.mapper.CharacterDetailsMapper
import com.dve.sari.minipokedex.domain.mapper.CharacterDetailsMapperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MiniPokedexModule {
    @Provides
    @Singleton
    fun providePokedexApi(
        builder: Retrofit.Builder
    ): MiniPokedexApi {
        return builder
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun providePokedexRepository(
        pokedexRepositoryImpl: MiniPokedexRepositoryImpl
    ): MiniPokedexRepository = pokedexRepositoryImpl

    @Provides
    @Singleton
    fun provideCharactersMapper(
        charactersMapperImpl: AllPokemonMapperImpl
    ): AllPokemonMapper = charactersMapperImpl

    @Provides
    @Singleton
    fun provideCharacterDetailsMapper(
        characterDetailsMapperImpl: CharacterDetailsMapperImpl
    ): CharacterDetailsMapper = characterDetailsMapperImpl
}