package com.example.pokerTournament.model

import com.example.pokerTournament.R

data class PokerTournament(
    var id: Int,
    var name: String,
    val playersMax: Int,
    val description: String,
    val ante: Boolean,
    val levels: String,
    val startingStack: Int,
    val imageResourceId: Int
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
            imageResourceId = R.drawable.famousfivess
        ),
        PokerTournament(
            id = 2,
            name = "Sweet sixes",
            playersMax = 9,
            description = "There's nothing like a turbo tournament to try to make a deep run without spending hours on it!",
            ante = false,
            levels = "10min",
            startingStack = 60000,
            imageResourceId = R.drawable.sweet_sixes
        ),
        PokerTournament(
            id = 3,
            name = "Lucky sevens",
            playersMax = 7,
            description = "This tournament is very popular amongst players and is played with 7 players at the table. So you will have to play more hands to avoid the blinds catching up with you!",
            ante = false,
            levels = "15min",
            startingStack = 70000,
            imageResourceId = R.drawable.lucky_sevens
        ),
        PokerTournament(
            id = 4,
            name = "Crazy eights",
            playersMax = 8,
            description = "The Crazy Eights lives up to its name: an affordable buy-in, large fields and an awesome atmosphere ... everything you need for a great night!",
            ante = true,
            levels = "20min",
            startingStack = 80000,
            imageResourceId = R.drawable.crazy_eights
        ),
        PokerTournament(
            id = 5,
            name = "Pokah Nines",
            playersMax = 9,
            description = "Back to the basics, as they say! This Daily, which carries the house name, is nothing more than a good old 9-max.",
            ante = false,
            levels = "15min",
            startingStack = 90000,
            imageResourceId = R.drawable.pokah_nines
        ),
        PokerTournament(
            id = 6,
            name = "Serious Tens",
            playersMax = 9,
            description = "A big stack of chips and turbo levels: that's the Serious Tens formula! There is no doubt about it, if you like action, this is the tournament for you!",
            ante = false,
            levels = "12min",
            startingStack = 10000,
            imageResourceId = R.drawable.serious_tens_1
        ),
        PokerTournament(
            id = 7,
            name = "The Queen's Millions",
            playersMax = 9,
            description = "With its ultra-deep structure, the Millions is undoubtedly the most prestigious. Every Saturday, the winner of this tournament is guaranteed to walk away with a very nice prize... Will you be next?",
            ante = true,
            levels = "20min",
            startingStack = 1000000,
            imageResourceId = R.drawable.the_queens_millions
        ),
        PokerTournament(
            id = 8,
            name = "Shooting Kings",
            playersMax = 9,
            description = "Fancy hunting bounties? Then our Shooting Kings is really for you! Take out as many opponents as possible and go home with a big prize.",
            ante = false,
            levels = "15min",
            startingStack = 50000,
            imageResourceId = R.drawable.shooting_kings
        ),
        PokerTournament(
            id = 9,
            name = "Tag Team",
            playersMax = 5,
            description = "This tournament is played in teams of 2. The principle is simple: one chip stack per team! One player plays the hand preflop and on the turn, while the second player makes the decisions on the flop and river. ",
            ante = false,
            levels = "20min",
            startingStack = 80000,
            imageResourceId = R.drawable.tag_team
        ),
        PokerTournament(
            id = 10,
            name = "WSOP Main Event",
            playersMax = 9,
            description = "This tournament is played in teams of 2. The principle is simple: one chip stack per team! One player plays the hand preflop and on the turn, while the second player makes the decisions on the flop and river. ",
            ante = true,
            levels = "120min",
            startingStack = 60000,
            imageResourceId = R.drawable.wsop_main_event
        ),
        PokerTournament(
            id = 11,
            name = "WPT Main Event",
            playersMax = 9,
            description = "The flagship event of the World Poker Tour, attracting top players from around the globe to compete for the coveted WPT title and a massive prize pool.",
            ante = true,
            levels = "60min",
            startingStack = 100000,
            imageResourceId = R.drawable.wpt_main_event
        ),
        PokerTournament(
            id = 12,
            name = "FPS Main Event",
            playersMax = 8,
            description = "Paris. The world’s most romantic city. Perfect for honeymoons, getaways…and live poker.",
            ante = true,
            levels = "40min",
            startingStack = 30000,
            imageResourceId = R.drawable.fps_main_event
        ),
        PokerTournament(
            id = 13,
            name = "PCA Main Event Qualifier",
            playersMax = 9,
            description = "This event makes you able to qualify for the PCA Main Event",
            ante = true,
            levels = "20min",
            startingStack = 15000,
            imageResourceId = R.drawable.pokerstars_pca_2023
        ),
        PokerTournament(
            id = 14,
            name = "Assie Millions Main Event",
            playersMax = 9,
            description = "The Aussie Millions Main Event is the highlight of the Southern Hemisphere's poker calendar. Held in vibrant Melbourne, it attracts pros and amateurs alike to compete for fame, fortune, and the prestigious Aussie Millions title.",
            ante = true,
            levels = "90min",
            startingStack = 30000,
            imageResourceId = R.drawable.assie_millions_main_event
        ),
        PokerTournament(
            id = 15,
            name = "PGT Heads-Up Showdown",
            playersMax = 2,
            description = "\nThe PGT Heads Up Showdown is an electrifying one-on-one battle of wits, where players go head-to-head to showcase their poker prowess. With intense strategy and high-stakes action, it's the ultimate test of skill and nerve on the poker circuit.",
            ante = false,
            levels = "20min",
            startingStack = 200000,
            imageResourceId = R.drawable.pgt_heads_up
        )
    )
}