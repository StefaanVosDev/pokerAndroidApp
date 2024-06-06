package com.example.pokerTournament.di

import android.content.Context
import com.example.pokerTournament.data.NetworkPlayerRepository
import com.example.pokerTournament.data.NetworkPokerTournamentRepository
import com.example.pokerTournament.data.PlayerRepository
import com.example.pokerTournament.data.PokerTournamentRepository
import com.example.pokerTournament.data.SettingsRepository
import com.example.pokerTournament.network.PlayerApiService
import com.example.pokerTournament.network.PokerApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val BASE_URL =  "http://10.0.2.2:3000/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun providePokerApiService(retrofit: Retrofit): PokerApiService {
        return retrofit.create(PokerApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePokerTournamentRepository(pokerApiService: PokerApiService): PokerTournamentRepository {
        return NetworkPokerTournamentRepository(pokerApiService)
    }

    @Provides
    @Singleton
    fun providePlayerApiService(retrofit: Retrofit): PlayerApiService {
        return retrofit.create(PlayerApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePlayerRepository(playerApiService: PlayerApiService): PlayerRepository {
        return NetworkPlayerRepository(playerApiService)
    }

    @Provides
    @Singleton
    fun providePreferencesRepository(@ApplicationContext context: Context): SettingsRepository {
        return SettingsRepository(context)
    }
}