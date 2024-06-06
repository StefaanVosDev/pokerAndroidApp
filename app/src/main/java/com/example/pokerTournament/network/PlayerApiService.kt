package com.example.pokerTournament.network

import com.example.pokerTournament.model.Player
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PlayerApiService {
    @GET("players")
    suspend fun getPlayers(): Response<List<Player>>
}