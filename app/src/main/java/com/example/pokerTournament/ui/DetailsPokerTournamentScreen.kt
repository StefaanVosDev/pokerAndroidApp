package com.example.pokerTournament.ui

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokerTournament.R
import com.example.pokerTournament.data.PokerTournamentUiState
import com.example.pokerTournament.model.Player
import com.example.pokerTournament.model.PokerTournament
import com.example.pokerTournament.model.PokerViewModel


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun DetailsPokerTournamentScreen(
    id: Int,
    uiState: List<PokerTournamentUiState>,
    viewModel: PokerViewModel,
    onDeleteClick: (PokerTournament) -> Unit,
    onEditClick: (Int) -> Unit,
    onSave: (PokerTournament) -> Unit,
    modifier: Modifier = Modifier,
    ) {
    var currentId by rememberSaveable { mutableIntStateOf(id) }
    val currentUiState = uiState.first { it.pokerTournament.id == currentId }

    val players = remember(currentId) { viewModel.getPlayersByTournamentId(currentId) }
    var showDialog by remember { mutableStateOf(false) }

    val pokerTournament = currentUiState.pokerTournament

    var image by rememberSaveable { mutableIntStateOf(pokerTournament.imageResourceId) }
    var name by rememberSaveable { mutableStateOf(pokerTournament.name) }
    var playersMax by rememberSaveable { mutableIntStateOf(pokerTournament.playersMax) }
    var startingStack by rememberSaveable { mutableIntStateOf(pokerTournament.startingStack) }
    var levels by rememberSaveable { mutableStateOf(pokerTournament.levels) }
    var ante by rememberSaveable { mutableStateOf(pokerTournament.ante) }


    LaunchedEffect(pokerTournament) {
        image = pokerTournament.imageResourceId
    }

    if (showDialog) {
        AddTournamentDialog(
            onAdd = { newTournament ->
                viewModel.addPokerTournament(newTournament)
                showDialog = false
            },
            onDismiss = { showDialog = false }
        )
    }
    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            FlippableCard(
                image,
                pokerTournament.description,
                currentId
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (currentUiState.isEditing) {
                OutlinedTextField(
                    value = name,
                    singleLine = true,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = playersMax.toString(),
                    singleLine = true,
                    onValueChange = { playersMax = it.toInt() },
                    label = { Text("Max Players") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = startingStack.toString(),
                    singleLine = true,
                    onValueChange = { startingStack = it.toInt() },
                    label = { Text("Starting Stack") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = levels,
                    singleLine = true,
                    onValueChange = { levels = it },
                    label = { Text("Levels") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(stringResource(id = R.string.ante))
                    Switch(
                        checked = ante,
                        onCheckedChange = { ante = it }
                    )
                }
            } else {
                TournamentInfoText(viewModel, currentId)
            }

            if (players.isNotEmpty()) {
                LazyRow(modifier = Modifier.fillMaxWidth()) {
                    items(players) { player ->
                        PlayerItem(player)
                    }
                }
            } else {
                Spacer(modifier = Modifier.height(16.dp))
            }
            Spacer(modifier.weight(1f))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { currentId = viewModel.findPreviousId(currentId)
                    },
                    colors = ButtonDefaults.buttonColors()
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.previous)
                    )
                }

                if (currentUiState.isEditing) {
                    Button(
                        onClick = {
                            onSave(
                                pokerTournament.copy(
                                    name = name,
                                    playersMax = playersMax,
                                    startingStack = startingStack,
                                    levels = levels,
                                    ante = ante
                                )
                            )
                        },
                        colors = ButtonDefaults.buttonColors()
                    ) {
                        Icon(
                            Icons.Filled.Save,
                            contentDescription = stringResource(id = R.string.update)
                        )
                    }
                } else {
                    Button(
                        onClick = {
                            onEditClick(currentId)
                        },
                        colors = ButtonDefaults.buttonColors()
                    ) {
                        Icon(
                            Icons.Filled.Edit,
                            contentDescription = stringResource(R.string.edit)
                        )
                    }
                }
                Button(
                    onClick = {
                        currentId = viewModel.findNextId(currentId)
                    },
                    colors = ButtonDefaults.buttonColors()
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = stringResource(id = R.string.next)
                    )
                }
                Button(
                    onClick =  {
                        onDeleteClick(viewModel.getPokerTournamentById(currentId))
                        currentId = viewModel.findPreviousId(currentId)
                               },
                    colors = ButtonDefaults.buttonColors()
                ) {
                    Icon(Icons.Filled.Delete, contentDescription = stringResource(R.string.delete))
                }
            }
        }
    }
}

@Composable
fun TournamentInfoText(pokerViewModel: PokerViewModel, currentId: Int) {
    Text(
        text = "Name: " + pokerViewModel.getPokerTournamentById(currentId).name,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(vertical = 4.dp),
        color = MaterialTheme.colorScheme.onSurface
    )
    Text(
        text = stringResource(id = R.string.max_players) + ": " + pokerViewModel.getPokerTournamentById(
            currentId
        ).playersMax,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(vertical = 4.dp),
        color = MaterialTheme.colorScheme.onSurface
    )
    Text(
        text = stringResource(id = R.string.current_players) + ": " + pokerViewModel.getPlayersByTournamentId(
            currentId
        ).size,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(vertical = 4.dp),
        color = MaterialTheme.colorScheme.onSurface
    )
    Text(
        text = stringResource(id = R.string.starting_stack) + ": " + pokerViewModel.getPokerTournamentById(
            currentId
        ).startingStack,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(vertical = 4.dp),
        color = MaterialTheme.colorScheme.onSurface
    )
    Text(
        text = stringResource(id = R.string.levels) + ": " + pokerViewModel.getPokerTournamentById(
            currentId
        ).levels,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(vertical = 4.dp),
        color = MaterialTheme.colorScheme.onSurface
    )
    Text(
        text = stringResource(id = R.string.ante) + ": " +
                 if (pokerViewModel.getPokerTournamentById(currentId)
                .ante) "Yes" else "No",
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(vertical = 4.dp),
        color = MaterialTheme.colorScheme.onSurface
    )
}


@Composable
fun PlayerItem(player: Player) {

    val image = player.imageResourceId

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
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


@Composable
fun FlippableCard(image: Int, description: String, currentId: Int) {
    var isFlipped by remember { mutableStateOf(false) }
    val editedRotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(500), label = stringResource(R.string.rotationy)
    )
    val editedAlpha by animateFloatAsState(
        targetValue = if (isFlipped) 0.2f else 1f,
        animationSpec = tween(500), label = stringResource(R.string.alpha)
    )
    LaunchedEffect(currentId) {
        isFlipped = false
    }

    Card(
        modifier = Modifier
            .size(300.dp)
            .clickable { isFlipped = !isFlipped },
        shape = MaterialTheme.shapes.medium
    ) {
        Box {
            Image(
                painter = painterResource(image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        rotationY = editedRotation
                        alpha = editedAlpha
                    }
            )
            if (isFlipped) {
                Text(
                    text = description,
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center),
                    textAlign = TextAlign.Center,
                    style =
                    TextStyle(
                        color = Color.Blue,
                        fontSize = 20.sp,
                        fontFamily = FontFamily.Serif
                    ),
                )
            }
        }
    }
}