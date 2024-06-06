package com.example.pokerTournament.data

import com.example.pokerTournament.model.Player
import com.example.pokerTournament.network.PlayerApiService
import retrofit2.HttpException

interface PlayerRepository {
    suspend fun getPlayers(): List<Player>
}

class NetworkPlayerRepository(private val playerApiService: PlayerApiService) : PlayerRepository {
    override suspend fun getPlayers(): List<Player> {
        try {
            val response = playerApiService.getPlayers()
            if (response.isSuccessful) {
                return response.body() ?: emptyList()
            }
            throw HttpException(response)
        } catch(e: Exception) {
            throw e
        }
    }
}