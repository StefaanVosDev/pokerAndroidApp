package com.example.artspaceproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspaceproject.model.PokerTournament
import com.example.artspaceproject.model.getPokerTournamentById
import com.example.artspaceproject.model.getPokerTournaments
import com.example.artspaceproject.ui.theme.ArtSpaceProjectTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import com.example.artspaceproject.model.getNumberOfPlayersByTournamentId
import com.example.ui2_stefaanvos.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceProjectTheme{
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PokerPage()
                }
            }
        }
    }
}

@Composable
fun PokerPage(modifier: Modifier = Modifier) {
    var currentId by remember { mutableStateOf(1) }

    val currentTournament = getPokerTournamentById(currentId)
    val image = when(currentId) {
        1 -> R.drawable.famousfivess
        2 -> R.drawable.sweet_sixes
        3 -> R.drawable.lucky_sevens
        4 -> R.drawable.crazy_eights
        5 -> R.drawable.pokah_nines
        6 -> R.drawable.serious_tens_1
        7 -> R.drawable.the_queens_millions
        8 -> R.drawable.shooting_kings
        9 -> R.drawable.tag_team
        10 -> R.drawable.wsop_main_event
        11 -> R.drawable.wpt_main_event
        12 -> R.drawable.fps_main_event
        13 -> R.drawable.pokerstars_pca_2023
        14 -> R.drawable.assie_millions_main_event
        else -> R.drawable.pgt_heads_up
    }

    Column (
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(image),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(16.dp))

        PokerTournamentInfo(
            pokerTournament = currentTournament,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.LightGray)
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Button(
                onClick = {
                    currentId =
                        if (currentId - 1 <= 0)
                            getPokerTournaments().size - 1
                        else
                            currentId - 1
                          },
            ) {
                Text(text = "Previous")
            }
            Button(
                onClick = {
                    currentId = (currentId +1 ) % getPokerTournaments().size
                },
            ) {
                Text(text = "Next")
            }
        }
    }
}

@Composable
fun PokerTournamentInfo(pokerTournament: PokerTournament, modifier: Modifier = Modifier){
    Column (modifier = modifier) {
        pokerTournament.let {
            Text(
                text = it.name,
                fontSize = 20.sp,
            )
            Text(
                text = "Max players: ${it.playersMax}",
                fontSize = 16.sp
            )
            Text(
                text = "Current players: ${getNumberOfPlayersByTournamentId(pokerTournament.id)}",
                fontSize = 16.sp
            )
            Text(
                text = it.description,
                fontSize = 14.sp
            )
        }
    }
}

@Preview
@Composable
fun PokerPagePreview() {
    PokerPage()
}