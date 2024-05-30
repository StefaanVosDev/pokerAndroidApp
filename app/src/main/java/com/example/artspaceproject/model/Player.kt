package com.example.artspaceproject.model

import androidx.annotation.DrawableRes

data class Player(
    val id: Int,
    val name: String,
    val nickname: String,
    val liveEarnings: Double,
    val dateOfBirth: String,
    val wsopBracelets: Int,
    @DrawableRes val imageResourceId: Int,
    val married: Boolean,
    val pokerTournamentId: Int
)