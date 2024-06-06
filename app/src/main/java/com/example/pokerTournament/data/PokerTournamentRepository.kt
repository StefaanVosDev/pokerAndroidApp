package com.example.pokerTournament.data

import com.example.pokerTournament.model.PokerTournament
import com.example.pokerTournament.network.PokerApiService
import okio.IOException
import retrofit2.HttpException

interface PokerTournamentRepository {
    suspend fun getPokerTournaments(): List<PokerTournament>
    suspend fun addPokerTournament(pokerTournament: PokerTournament): PokerTournament?
    suspend fun deletePokerTournament(id: Int): Boolean
    suspend fun updatePokerTournament(id: Int, pokerTournament: PokerTournament): PokerTournament?
}

class NetworkPokerTournamentRepository(private val pokerApiService: PokerApiService) : PokerTournamentRepository {
    override suspend fun getPokerTournaments(): List<PokerTournament> {
        try {
            val response = pokerApiService.getPokerTournaments()
            if (response.isSuccessful) {
                return response.body() ?: emptyList()
            }
            throw HttpException(response)
        } catch (e: IOException) {
            throw e
        } catch (e: HttpException) {
            throw e
        }
    }

    override suspend fun addPokerTournament(pokerTournament: PokerTournament): PokerTournament? {
        try {
            val response = pokerApiService.createPokerTournament(pokerTournament)
            if (response.isSuccessful) {
                return response.body()
            }
            throw HttpException(response)
        } catch (e: IOException) {
            throw e
        } catch (e: HttpException) {
            throw e
        }
    }

    override suspend fun deletePokerTournament(id: Int): Boolean {
        try {
            val response = pokerApiService.deletePokerTournament(id)
            return response.isSuccessful
        } catch (e: IOException) {
            throw e
        } catch (e: HttpException) {
            throw e
        }
    }

    override suspend fun updatePokerTournament(id: Int, pokerTournament: PokerTournament): PokerTournament? {
        try {
            val response = pokerApiService.updatePokerTournament(id, pokerTournament)
            if (response.isSuccessful) {
                return response.body()
            }
            throw HttpException(response)
        } catch (e: IOException) {
            throw e
        } catch (e: HttpException) {
            throw e
        }
    }
}