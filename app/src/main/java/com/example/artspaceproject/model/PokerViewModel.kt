package com.example.artspaceproject.model

import androidx.lifecycle.ViewModel
import com.example.artspaceproject.data.Datasource

class PokerViewModel : ViewModel()
{
    private val datasource = Datasource()

    fun getPokerTournaments() = datasource.loadPokerTournaments()

    fun getPokerPlayersByTournamentId(tournamentId: Int): List<Player> {
        return datasource.loadPlayers()
            .filter { it.pokerTournamentId == tournamentId }
    }

    fun getNumberOfPlayersByTournamentId(tournamentId: Int): Int {
        return datasource.loadPlayers()
            .count {it.pokerTournamentId == tournamentId
            }
    }

    fun getPokerTournamentById(tournamentId: Int): PokerTournament {
        return datasource.loadPokerTournaments()
            .first { pokerTournament -> pokerTournament.id == tournamentId };
    }
}