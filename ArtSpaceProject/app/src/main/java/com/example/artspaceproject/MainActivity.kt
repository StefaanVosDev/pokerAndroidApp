package com.example.artspaceproject

import android.os.Bundle
import android.text.InputFilter.AllCaps
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.mutableIntStateOf
import com.example.artspaceproject.model.getNumberOfPlayersByTournamentId

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
    var currentId by remember { mutableIntStateOf(1) }

    val currentTournament = getPokerTournamentById(currentId)

    Column (
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(id = R.drawable.famousfivess),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )
        PokerInfo(
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
                .padding(horizontal = 16.dp)
        ) {
            Button(onClick = {
                currentId = if (currentId - 1 < 0) getPokerTournaments().size - 1 else currentId - 1
            }) {
                Text(text = "Previous")
            }
            Button(onClick = {
                currentId = (currentId +1 ) % getPokerTournaments().size
            }) {
                Text(text = "Next")
            }
        }
    }
}

@Composable
fun PokerInfo(pokerTournament: PokerTournament?, modifier: Modifier = Modifier){
    Column (modifier = modifier) {
        pokerTournament?.let {
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