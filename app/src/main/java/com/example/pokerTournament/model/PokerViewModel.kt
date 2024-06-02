package com.example.pokerTournament.model

import androidx.lifecycle.ViewModel
import com.example.pokerTournament.data.PokerTournamentUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class PokerViewModel : ViewModel()
{
    companion object {
        private const val TAG = "PokerViewModel"
    }

    private val _uiState = MutableStateFlow<List<PokerTournamentUiState>>(getInitialPokerTournaments())
    val uiState: StateFlow<List<PokerTournamentUiState>> = _uiState

    private fun getInitialPokerTournaments(): List<PokerTournamentUiState> {
        return getPokerTournaments().map { PokerTournamentUiState(it) }
    }

    fun addPokerTournament(pokerTournament: PokerTournament) {
        pokerTournament.id = _uiState.value.size + 1
        val newPokerTournamentState = PokerTournamentUiState(pokerTournament)
        _uiState.update { currentPokerTournaments ->
            currentPokerTournaments + newPokerTournamentState
        }
    }

    fun updatePokerTournament(updatedPokerTournament: PokerTournament) {
        _uiState.update { currentPokerTournaments ->
            currentPokerTournaments.map {
                if (it.pokerTournament.id == updatedPokerTournament.id) {
                    PokerTournamentUiState(updatedPokerTournament, it.isEditing)
                } else {
                    it
                }
            }
        }
    }

    fun deletePokerTournament(tournament: PokerTournament) {
        _uiState.update { currentPokerTournaments ->
            val filteredTournaments = currentPokerTournaments.filterNot { it.pokerTournament.id == tournament.id }
            val updatedTournaments = filteredTournaments.map { pokerTournamentUiState ->
                if (pokerTournamentUiState.pokerTournament.id > tournament.id) {
                    val updatedPokerTournament = pokerTournamentUiState.pokerTournament.copy(id = pokerTournamentUiState.pokerTournament.id - 1)
                    pokerTournamentUiState.copy(pokerTournament = updatedPokerTournament)
                } else {
                    pokerTournamentUiState
                }
            }
            updatedTournaments
        }
    }

    fun getPlayersByTournamentId(tournamentId: Int): List<Player> {
        return Players.filter { it.pokerTournamentId == tournamentId }
    }

    fun getPokerTournamentById(tournamentId: Int): PokerTournament {
        val values = uiState.value
        val first = values.first() {it.pokerTournament.id == tournamentId}
        println("first: $first")
        val element = values.first { it.pokerTournament.id >= tournamentId }
        println("element: $element")
        return uiState.value.first { it.pokerTournament.id >= tournamentId }.pokerTournament
    }
}