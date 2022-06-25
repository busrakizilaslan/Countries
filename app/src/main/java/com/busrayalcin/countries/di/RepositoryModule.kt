package com.busrayalcin.countries.di

import com.busrayalcin.countries.api.APIService
import com.busrayalcin.countries.repo.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideDataRepository(apiService: APIService): Repository {
        return Repository(apiService)
    }
}