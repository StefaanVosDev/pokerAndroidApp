package com.example.pokerTournament.network

import com.example.pokerTournament.model.PokerTournament
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PokerApiService {
    @GET("pokerTournaments")
    suspend fun getPokerTournaments(): Response<List<PokerTournament>>

    @POST("pokerTournaments")
    suspend fun createPokerTournament(@Body pokerTournament: PokerTournament): Response<PokerTournament>

    @PUT("pokerTournaments/{id}")
    suspend fun updatePokerTournament(@Path("id") id: Int, @Body pokerTournament: PokerTournament): Response<PokerTournament>

    @DELETE("pokerTournaments/{id}")
    suspend fun deletePokerTournament(@Path("id") id: Int): Response<Unit>
}