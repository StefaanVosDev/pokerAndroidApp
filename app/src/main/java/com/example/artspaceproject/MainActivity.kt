package com.example.artspaceproject

import android.content.res.Configuration
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.artspaceproject.model.PokerViewModel
import com.example.ui2_stefaanvos.R


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

    val configuration = LocalConfiguration.current
    val isLandScape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    val image = pokerTournaments[currentId - 1].imageResourceId

    val players = remember(currentId) { pokerViewModel.getPokerPlayersByTournamentId(currentId) }

    Scaffold(
        topBar = {
            PokerTopAppBar()
        }
    ) { paddingValues ->

        Box(modifier = modifier
            .padding(paddingValues)
            .fillMaxSize()) {
            if (isLandScape) {
                Row(
                    modifier = modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Top
                ) {
                    Image(
                        painter = painterResource(image),
                        contentDescription = null,
                        modifier = Modifier
                            .weight(1f)
                            .size(250.dp)
                    )

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 16.dp)
                    ) {

                        NameTextField(nameInput, onValueChange = { nameInput = it })
                            
                        TournamentInfoText(pokerViewModel, currentId)

                        LazyColumn(modifier = Modifier.weight(1f)) {
                            items(players) { player ->
                                PlayerItem(player)
                            }
                        }

                        NavigationButtons(
                            currentId = currentId,
                            pokerTournamentsSize = pokerTournaments.size,
                            onPreviousClick = {
                                currentId =
                                    if (currentId > 1)
                                        currentId - 1
                                    else
                                        pokerTournaments.size
                                nameInput = pokerViewModel.getPokerTournamentById(currentId).name
                            },
                            onNextClick = {
                                currentId =
                                    if (currentId < pokerTournaments.size)
                                        currentId + 1
                                    else
                                        1
                                nameInput = pokerViewModel.getPokerTournamentById(currentId).name
                            },
                            onUpdateClick = {
                                pokerTournaments[currentId - 1].name = nameInput
                            }
                        )
                    }
                }
            } else {
                Column(
                    modifier = modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                ) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Image(
                        painter = painterResource(image),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(300.dp)
                            .padding(horizontal = 16.dp),
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row {
                        NameTextField(nameInput, onValueChange = { nameInput = it })
                    }

                    TournamentInfoText(pokerViewModel, currentId)

                    if (players.isNotEmpty()) {
                        LazyRow(modifier = Modifier.fillMaxWidth()) {
                            items(players) { player ->
                                PlayerItem(player)
                            }
                        }
                    } else {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    Spacer(Modifier.weight(1f))

                    NavigationButtons(
                        currentId = currentId,
                        pokerTournamentsSize = pokerTournaments.size,
                        onPreviousClick = {
                            currentId =
                                if (currentId > 1)
                                    currentId - 1
                                else
                                    pokerTournaments.size
                            nameInput = pokerViewModel.getPokerTournamentById(currentId).name
                        },
                        onNextClick = {
                            currentId =
                                if (currentId < pokerTournaments.size)
                                    currentId + 1
                                else
                                    1
                            nameInput = pokerViewModel.getPokerTournamentById(currentId).name
                        },
                        onUpdateClick = {
                            pokerTournaments[currentId - 1].name = nameInput
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun NameTextField(nameInput: String, onValueChange: (String) -> Unit) {
    TextField(
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        value = nameInput,
        onValueChange = onValueChange,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth(0.8f),
        textStyle = TextStyle(fontSize = 18.sp),
        maxLines = 1
    )
}

@Composable
fun TournamentInfoText(pokerViewModel: PokerViewModel, currentId: Int) {
    Text(
        text = stringResource(id = R.string.max_players) + pokerViewModel.getPokerTournamentById(
            currentId
        ).playersMax,
        fontSize = 18.sp,
        modifier = Modifier.padding(vertical = 4.dp),
        color = Color.Black
    )
    Text(
        text = stringResource(id = R.string.current_players) + pokerViewModel.getNumberOfPlayersByTournamentId(
            currentId
        ),
        fontSize = 18.sp,
        modifier = Modifier.padding(vertical = 4.dp),
        color = Color.Black
    )
   Text(
        text = stringResource(id = R.string.starting_stack) + pokerViewModel.getPokerTournamentById(
            currentId
        ).startingStack,
        fontSize = 18.sp,
        modifier = Modifier.padding(vertical = 4.dp),
        color = Color.Black
    )
}

@Composable
fun NavigationButtons(
    currentId: Int,
    pokerTournamentsSize: Int,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    onUpdateClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(onClick = onPreviousClick) {
            Text(text = stringResource(id = R.string.previous))
        }
        Button(onClick = onUpdateClick) {
            Text(text = stringResource(id = R.string.update))
        }
        Button(onClick = onNextClick) {
            Text(text = stringResource(id = R.string.next))
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
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black
                )
                Text(
                    text = player.nickname,
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.Gray
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokerTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(65.dp)
                        .padding(8.dp),
                    contentDescription = null,
                    painter = painterResource(R.drawable.poker_icon),
                )
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.Black
                )
            }
        },
        modifier = modifier
    )
}


@Preview(locale = "nl")
@Composable
fun PokerPagePreview() {
    PokerPage()
}