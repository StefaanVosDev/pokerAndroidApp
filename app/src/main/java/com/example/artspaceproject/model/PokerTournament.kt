package com.example.artspaceproject.model

import androidx.annotation.DrawableRes

data class PokerTournament(
    val id: Int,
    var name: String,
    val playersMax: Int,
    val description: String,
    val ante: Boolean,
    val levels: String,
    val startingStack: Int,
    @DrawableRes val imageResourceId: Int
)