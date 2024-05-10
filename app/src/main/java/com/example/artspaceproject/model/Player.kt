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
            pokerTournamentId = 5
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
            pokerTournamentId = 5
        ),
        Player(
            id = 3,
            name = "Doyle Brunson",
            nickname = "Texas Dolly",
            liveEarnings = 6176737.63,
            dateOfBirth = "1933-05-14",
            wsopBracelets = 10,
            image = "https://upload.wikimedia.org/wikipedia/commons/thumb/3/3f/Doyle_Brunson.jpg/330px-Doyle_Brunson.jpg",
            married = true,
            pokerTournamentId = 3
        ),
        Player(
            id = 4,
            name = "Phil Ivey",
            nickname = "Tiger Woods of Poker",
            liveEarnings = 42556045.65,
            dateOfBirth = "1977-02-01",
            wsopBracelets = 10,
            image = "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7b/Phil_Ivey_%282009_WSOP%29.jpg/330px-Phil_Ivey_%282009_WSOP%29.jpg",
            married = false,
            pokerTournamentId = 6
        ),
        Player(
            id = 5,
            name = "Erik Seidel",
            nickname = "Sly, Seiborg",
            liveEarnings = 47515285.12,
            dateOfBirth = "1959-11-06",
            wsopBracelets = 10,
            image = "https://upload.wikimedia.org/wikipedia/commons/thumb/1/12/Erik_Seidel_2018.jpg/330px-Erik_Seidel_2018.jpg",
            married = true,
            pokerTournamentId = 7
        ),
        Player(
            id = 6,
            name = "Jason Mercier",
            nickname = "Treysfull21",
            liveEarnings = 20696578.73,
            dateOfBirth = "1986-11-12",
            wsopBracelets = 6,
            image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSG_vWhDrCPW89YHFvsuHSW5Ui5WJgey1wSFw&s",
            married = false,
            pokerTournamentId = 9
        ),
        Player(
            id = 7,
            name = "Barry Greenstein",
            nickname = "The Robin Hood of Poker",
            liveEarnings = 8612332.61,
            dateOfBirth = "1954-12-30",
            wsopBracelets = 3,
            image = "https://upload.wikimedia.org/wikipedia/commons/thumb/e/ea/BarryGreenstein.jpg/330px-BarryGreenstein.jpg",
            married = true,
            pokerTournamentId = 10
        ),
        Player(
            id = 8,
            name = "Phil Laak",
            nickname = "The Unabomber",
            liveEarnings = 3909419.98,
            dateOfBirth = "1972-09-08",
            wsopBracelets = 1,
            image = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/25/Phil_The_Unabomber_Laak.jpg/330px-Phil_The_Unabomber_Laak.jpg",
            married = true,
            pokerTournamentId = 4
        ),
        Player(
            id = 9,
            name = "Antanas Guoga",
            nickname = "Tony G",
            liveEarnings = 10132105.18,
            dateOfBirth = "1973-12-17",
            wsopBracelets = 0,
            image = "https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Tony_G_the_Poker_Player.jpg/399px-Tony_G_the_Poker_Player.jpg",
            married = false,
            pokerTournamentId = 1
        ),
        Player(
            id = 10,
            name = "Justin Bonomo",
            nickname = "ZeeJustin",
            liveEarnings = 63405741.43,
            dateOfBirth = "1985-09-30",
            wsopBracelets = 0,
            image = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/88/Justin_Bonomo_2018.jpg/399px-Justin_Bonomo_2018.jpg",
            married = false,
            pokerTournamentId = 12
        )
    )
}
