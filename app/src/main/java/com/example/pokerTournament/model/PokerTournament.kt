package com.example.pokerTournament.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("PokerTournament")
data class PokerTournament(
    var id: Int,
    val name: String,
    val playersMax: Int,
    val description: String,
    val ante: Boolean,
    val levels: String,
    val startingStack: Int,
    @SerialName("image")
    val imageResourceId: String
)