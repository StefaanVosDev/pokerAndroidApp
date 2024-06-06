package com.example.pokerTournament.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Player")
data class Player(
    val id: Int,
    val name: String,
    val nickname: String,
    val liveEarnings: Double,
    val dateOfBirth: String,
    val wsopBracelets: Int,
    val image: String,
    val married: Boolean,
    val pokerTournamentId: Int
)