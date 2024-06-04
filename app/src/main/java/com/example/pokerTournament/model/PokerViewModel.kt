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
            currentPokerTournaments.filterNot { it.pokerTournament.id == tournament.id }
        }
    }

    fun getPlayersByTournamentId(tournamentId: Int): List<Player> {
        return Players.filter { it.pokerTournamentId == tournamentId }
    }

    fun getPokerTournamentById(tournamentId: Int): PokerTournament {
        return uiState.value.first { it.pokerTournament.id >= tournamentId }.pokerTournament
    }

    fun toggleExpand(tournamentId: Int) {
        _uiState.update { currentPokerTournaments ->
            currentPokerTournaments.map {
                if (it.pokerTournament.id == tournamentId) {
                    it.copy(isExpanded = !it.isExpanded)
                } else {
                    it
                }
            }
        }
    }

    fun startEditMode(tournamentId: Int) {
        _uiState.update { currentPokerTournaments ->
            currentPokerTournaments.map {
                if (it.pokerTournament.id == tournamentId) {
                    it.copy(isEditing = true)
                } else {
                    it
                }
            }
        }
    }

    fun savePokerTournament(pokerTournament: PokerTournament) {
        _uiState.update { currentPokerTournaments ->
            currentPokerTournaments.map {
                if (it.pokerTournament.id == pokerTournament.id) {
                    PokerTournamentUiState(pokerTournament, false)
                } else {
                    it
                }
            }
        }
    }

    fun findNextId(currentId: Int): Int {
        val id =  uiState.value.firstOrNull { it.pokerTournament.id > currentId }?.pokerTournament?.id
        return if (id!=null) {
            id
        } else {
            uiState.value.first().pokerTournament.id
        }
    }

    fun findPreviousId(currentId: Int): Int {
        val newId = uiState.value.lastOrNull { it.pokerTournament.id < currentId }?.pokerTournament?.id
        return if (newId!=null) {
            newId
        } else {
            uiState.value.last().pokerTournament.id
        }
    }
}