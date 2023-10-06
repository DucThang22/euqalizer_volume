package com.example.hahalolofake.data.repository

import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun providesCharacterRepository(characterRepositoryImpl: CharacterRepositoryImpl): CharacterRepository

//    @Singleton
//    @Binds
//    abstract fun providesEffectRepository(effectRepositoryImpl: EffectRepositoryImpl): EffectRepository

}