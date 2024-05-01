package com.example.artspaceproject.model

import android.media.Image

class PokerTournament(
    val id: Int,
    val name: String,
    val playersMax: Int,
    val description: String,
    val ante: Boolean,
    val levels: String,
    val startingStack: Int,
    val image: String
)


fun getPokerTournaments(): List<PokerTournament> {
    return listOf(
        PokerTournament(
            id = 1,
            name = "Famous Fives",
            playersMax = 9,
            description = "The Famous Fives is THE tournament for Deep Turbo fans. You start with a lot of chips, but the blinds go up fast! An ideal playground for fans of action at the poker table.",
            ante = false,
            levels = "20min",
            startingStack = 500000,
            image = "https://pokahnights.com/wp-content/uploads/2023/06/famous-fivess.jpg"
        ),
        PokerTournament(
            id = 2,
            name = "Sweet sixes",
            playersMax = 9,
            description = "There's nothing like a turbo tournament to try to make a deep run without spending hours on it!",
            ante = false,
            levels = "10min",
            startingStack = 60000,
            image = "https://pokahnights.com/wp-content/uploads/2023/04/sweet-sixes.jpg"
        ),

    )
}

fun getNumberOfPlayersByTournamentId(tournamentId: Int): Int {
    return getPlayerList().count {
        it.pokerTournamentId == tournamentId
    }
}

fun getPokerTournamentById(tournamentId: Int): PokerTournament? {
    return getPokerTournaments().find {
        it.id == tournamentId
    }
}