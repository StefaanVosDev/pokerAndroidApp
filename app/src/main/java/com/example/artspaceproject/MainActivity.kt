package com.example.artspaceproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspaceproject.model.getPokerTournamentById
import com.example.artspaceproject.model.getPokerTournaments
import com.example.artspaceproject.ui.theme.ArtSpaceProjectTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
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
    var pokerTournaments = remember { getPokerTournaments() };
    var currentId by remember { mutableStateOf(1) }
    var nameInput by remember(currentId) { mutableStateOf(pokerTournaments[currentId-1].name) }

    val image = when (currentId) {
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

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp),
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row {
            TextField(
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                value = nameInput,
                onValueChange = { nameInput = it},
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .align(Alignment.CenterVertically)
                , textStyle = TextStyle(fontSize = 35.sp)
            )
        }
        Text(
            text = "Max players: ${getPokerTournamentById(currentId).playersMax}",
            fontSize = 18.sp,
            modifier = Modifier.padding(vertical = 4.dp)

        )
        Text(
            text = "Current players: ${getNumberOfPlayersByTournamentId(currentId)}",
            fontSize = 18.sp,
            modifier = Modifier.padding(vertical = 4.dp)
        )
        Text(
            text = "Starting stack: "+ getPokerTournamentById(currentId).startingStack,
            fontSize = 18.sp,
            modifier = Modifier.padding(vertical = 4.dp)

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
                            pokerTournaments.size - 1
                        else
                            currentId - 1
                    nameInput = getPokerTournamentById(currentId).name
                }
            ) {
                Text(text = "Previous")
            }
            Button(onClick = {
                pokerTournaments[currentId - 1].name = nameInput

            }) {
                Text(text = "Update")
            }
            Button(
                onClick = {
                    currentId = (currentId + 1) % pokerTournaments.size
                    nameInput = getPokerTournamentById(currentId).name
                }
            ) {
                Text(text = "Next")
            }
        }
    }
}

@Preview
@Composable
fun PokerPagePreview() {
    PokerPage()
}