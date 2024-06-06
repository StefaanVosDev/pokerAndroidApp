package com.example.pokerTournament.data

import com.example.pokerTournament.model.PokerTournament

sealed interface PokerUiState {
    data class Success(
        val pokerTournaments: List<PokerTournament>,
        val userMessage: String? = null
        ): PokerUiState
    object Loading: PokerUiState
    object Error: PokerUiState
}