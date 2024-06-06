package com.example.pokerTournament.data

import android.content.Context
import com.example.pokerTournament.network.PlayerApiService
import com.example.pokerTournament.network.PokerApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val pokerTournamentRepository: PokerTournamentRepository
    val playerRepository: PlayerRepository
}

class DefaultAppContainer(context: Context) : AppContainer {

    private val BASE_URL =  "http://10.0.2.2:3000/"


    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()

    private val pokerTournamentApiService : PokerApiService by lazy {
        retrofit.create(PokerApiService::class.java)
    }
    private val playerApiService : PlayerApiService by lazy {
        retrofit.create(PlayerApiService::class.java)
    }

    override val pokerTournamentRepository: PokerTournamentRepository by lazy {
        NetworkPokerTournamentRepository(pokerTournamentApiService)
    }
    override val playerRepository: PlayerRepository by lazy {
        NetworkPlayerRepository(playerApiService)
    }

    val settingsRepository = SettingsRepository(context)
}

