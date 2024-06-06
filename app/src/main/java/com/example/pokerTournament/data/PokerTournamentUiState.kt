package com.example.pokerTournament.data

import com.example.pokerTournament.model.Player
import com.example.pokerTournament.model.PokerTournament

data class PokerTournamentUiState(
    val pokerTournament: PokerTournament,
    val players: List<Player> = emptyList(),
    val isEditing: Boolean = false,
    val isExpanded: Boolean = false
)