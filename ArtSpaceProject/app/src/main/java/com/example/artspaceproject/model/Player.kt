package com.example.artspaceproject.model

class Player(
    val id: Int,
    val name: String,
    val nickname: String,
    val liveEarnings: Double,
    val dateOfBirth: String,
    val wsopBracelets: Int,
    val image: String,
    val married: Boolean,
    val pokerTournamentId: Int
)


fun getPlayerList(): List<Player> {
    return listOf(
        Player(
            id = 1,
            name = "Daniel Negraunu",
            nickname = "Kid Poker",
            liveEarnings = 52628149.50,
            dateOfBirth = "1974-07-26",
            wsopBracelets = 6,
            image = "https://upload.wikimedia.org/wikipedia/commons/f/ff/Daniel_Negreanu_2007.jpg?20220308215854",
            married = true,
            pokerTournamentId = 2
        ),
        Player(
            id = 2,
            name = "Phil Hellmuth",
            nickname = "The Poker Brat",
            liveEarnings = 26735185.42,
            dateOfBirth = "1964-07-16",
            wsopBracelets = 17,
            image = "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1d/Phil_Hellmuth_2021.jpg/330px-Phil_Hellmuth_2021.jpg",
            married = true,
            pokerTournamentId = 1
        )
    )
}
