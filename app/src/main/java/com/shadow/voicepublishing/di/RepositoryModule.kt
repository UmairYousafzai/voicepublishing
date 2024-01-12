package com.shadow.voicepublishing.di

import com.shadow.voicepublishing.repositories.auth.abstraction.AuthRepository
import com.shadow.voicepublishing.repositories.auth.implementation.AuthRepoImpl
import com.shadow.voicepublishing.repositories.data.abstraction.DataStoreRepository
import com.shadow.voicepublishing.repositories.data.implementation.DataStoreRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {

    @Binds
    fun bindAuthRepository(impl: AuthRepoImpl): AuthRepository

    @Binds
    fun bindDataStoreRepository(impl: DataStoreRepoImpl): DataStoreRepository
}
