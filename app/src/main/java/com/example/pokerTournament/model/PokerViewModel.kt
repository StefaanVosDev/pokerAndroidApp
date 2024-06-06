package com.example.pokerTournament.model

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokerTournament.data.PlayerRepository
import com.example.pokerTournament.data.PokerTournamentRepository
import com.example.pokerTournament.data.PokerTournamentUiState
import com.example.pokerTournament.data.PokerUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokerViewModel @Inject constructor(
    private val pokerTournamentRepository: PokerTournamentRepository,
    private val playerRepository: PlayerRepository) : ViewModel() {

    private val pokerTournamentList = MutableStateFlow(listOf<PokerTournament>())
    val playersList = MutableStateFlow<List<Player>>(emptyList())
    var pokerUiState: PokerUiState by mutableStateOf(PokerUiState.Loading)

    private val _uiState = MutableStateFlow<List<PokerTournamentUiState>>(emptyList())
    val uiState: StateFlow<List<PokerTournamentUiState>> = _uiState.asStateFlow()

    init {
        getPokerTournaments()
    }

    private fun getPokerTournaments() {
        viewModelScope.launch {
            try {
                val pokerTournaments = pokerTournamentRepository.getPokerTournaments()
                pokerTournamentList.value = pokerTournaments

                _uiState.value = pokerTournaments.map { PokerTournamentUiState(it) }
            } catch (e: Exception) {
                pokerUiState = PokerUiState.Error
                Log.d(TAG, "Error getting poker tournaments", e)
            }
        }
    }

    fun addPokerTournament(pokerTournament: PokerTournament) {
        viewModelScope.launch {
            try {
                val newPokerTournament: PokerTournament? =
                    pokerTournamentRepository.addPokerTournament(pokerTournament)
                successMessage("New itCompany added id=${newPokerTournament?.id}")
                getPokerTournaments()

            } catch (e: Exception) {
                pokerUiState = PokerUiState.Error
                Log.d(TAG, "Error adding poker tournament", e)
            }
        }
    }

    private fun successMessage(message: String) {
        if (pokerUiState is PokerUiState.Success) {
            val currentState = pokerUiState as PokerUiState.Success
            pokerUiState = PokerUiState.Success(currentState.pokerTournaments, message)
        }
    }

    fun deletePokerTournament(pokerTournament: PokerTournament) {
        viewModelScope.launch {
            val deleted = pokerTournamentRepository.deletePokerTournament(pokerTournament.id)
            if (deleted) {
                successMessage("Poker tournament deleted id=${pokerTournament.id}")
                getPokerTournaments()
            }
        }
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
                    PokerTournamentUiState(pokerTournament, isEditing = false)
                } else {
                    it
                }
            }
        }
    }

    fun getPlayersListByPokerTournamentId(tournamentId: Int) {
        viewModelScope.launch{
            pokerUiState = PokerUiState.Loading
            try {
                val players = playerRepository.getPlayers()
                val filteredPlayers = players.filter { it.pokerTournamentId == tournamentId }

                playersList.value = filteredPlayers

                _uiState.update { currentPokerTournaments ->
                    currentPokerTournaments.map {
                        if (it.pokerTournament.id == tournamentId) {
                            it.copy(players = filteredPlayers)
                        } else {
                            it
                        }
                    }
                }
                pokerUiState = PokerUiState.Success(pokerTournamentList.value)
            } catch (e: Exception) {
                pokerUiState = PokerUiState.Error
                Log.d(TAG, "Error getting players", e)
            }
        }
    }

    fun findNextId(currentId: Int): Int {
        val newId =
            uiState.value.firstOrNull { it.pokerTournament.id > currentId }?.pokerTournament?.id
        return newId ?: uiState.value.first().pokerTournament.id
    }

    fun findPreviousId(currentId: Int): Int {
        val newId =
            uiState.value.lastOrNull { it.pokerTournament.id < currentId }?.pokerTournament?.id
        return newId ?: uiState.value.last().pokerTournament.id
    }

    companion object {
        private const val TAG = "PokerViewModel"
    }
}