package com.example.civicseva.di

import android.content.Context
import com.example.civicseva.core.network.ApiService
import com.example.civicseva.data.repository.UserPreferencesRepository
import com.example.civicseva.data.repository.dataStore
import com.example.civicseva.data.repository.AuthRepository
import com.example.civicseva.data.repository.AuthRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideUserPreferencesRepository(@ApplicationContext context: Context): UserPreferencesRepository {
        return UserPreferencesRepository(context.dataStore)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        apiService: ApiService,
        userPrefs: UserPreferencesRepository
    ): AuthRepository {
        return AuthRepositoryImp(apiService, userPrefs)
    }
}