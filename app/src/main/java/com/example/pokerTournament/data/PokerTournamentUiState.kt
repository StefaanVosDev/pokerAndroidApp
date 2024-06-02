package com.example.pokerTournament.data

import com.example.pokerTournament.model.PokerTournament

data class PokerTournamentUiState(
    val pokerTournament: PokerTournament,
    val isEditing: Boolean = false
)