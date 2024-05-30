package com.example.artspaceproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
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
import com.example.artspaceproject.ui.theme.ArtSpaceProjectTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import com.example.artspaceproject.model.Player
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import com.example.artspaceproject.model.PokerViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceProjectTheme {
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
fun PokerPage(modifier: Modifier = Modifier, pokerViewModel: PokerViewModel = PokerViewModel()) {
    val pokerTournaments = remember { pokerViewModel.getPokerTournaments() }
    var currentId by remember { mutableStateOf(1) }
    var nameInput by remember(currentId) { mutableStateOf(pokerTournaments[currentId - 1].name) }

    val image = pokerTournaments[currentId-1].imageResourceId

    val players = remember(currentId) { pokerViewModel.getPokerPlayersByTournamentId(currentId) }
    Box(modifier = modifier.fillMaxSize()) {
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
                    onValueChange = { nameInput = it },
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .align(Alignment.CenterVertically), textStyle = TextStyle(fontSize = 35.sp)
                )
            }
            Text(
                text = "Max players: ${pokerViewModel.getPokerTournamentById(currentId).playersMax}",
                fontSize = 18.sp,
                modifier = Modifier.padding(vertical = 4.dp)

            )
            Text(
                text = "Current players: ${pokerViewModel.getNumberOfPlayersByTournamentId(currentId)}",
                fontSize = 18.sp,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(
                text = "Starting stack: " + pokerViewModel.getPokerTournamentById(currentId).startingStack,
                fontSize = 18.sp,
                modifier = Modifier.padding(vertical = 4.dp)

            )

            LazyRow(modifier = Modifier.fillMaxWidth()) {
                items(players) { player ->
                    PlayerItem(player)
                }
            }

            Spacer(Modifier.weight(1f))

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
                            if (currentId > 1)
                                currentId - 1
                            else
                                pokerTournaments.size
                        nameInput = pokerViewModel.getPokerTournamentById(currentId).name
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
                        currentId =
                            if (currentId < pokerTournaments.size)
                                currentId + 1
                            else
                                1
                        nameInput = pokerViewModel.getPokerTournamentById(currentId).name
                    }
                ) {
                    Text(text = "Next")
                }
            }
        }
    }
}

@Composable
fun PlayerItem(player: Player) {

    val image = player.imageResourceId

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(image),
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = player.name,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = player.nickname,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}

@Preview
@Composable
fun PokerPagePreview() {
    PokerPage()
}