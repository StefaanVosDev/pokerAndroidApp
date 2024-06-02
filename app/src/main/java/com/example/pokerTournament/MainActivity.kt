package com.example.pokerTournament

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.example.pokerTournament.ui.theme.ArtSpaceProjectTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import com.example.pokerTournament.model.Player
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.pokerTournament.model.PokerViewModel
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import com.example.pokerTournament.model.PokerTournament
import androidx.lifecycle.viewmodel.compose.viewModel


private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate: called")
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.onBackground
                ) {
                    PokerPage()
                }
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart: called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: called")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: called")
    }
}

@Composable
fun PokerPage(viewModel: PokerViewModel = viewModel()) {
    var showDialog by rememberSaveable { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsState()

    var currentId by rememberSaveable { mutableIntStateOf(1) }
    var nameInput by remember(currentId) { mutableStateOf(viewModel.getPokerTournamentById(currentId).name) }

    val image = viewModel.getPokerTournamentById(currentId).imageResourceId
    val players = remember(currentId) { viewModel.getPlayersByTournamentId(currentId) }

    if (showDialog) {
        AddTournamentDialog(
            onAdd = { newTournament ->
                viewModel.addPokerTournament(newTournament)
                showDialog = false
            },
            onDismiss = { showDialog = false }
        )
    }
    Scaffold(
        topBar = {
            PokerTopAppBar()
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                FlippableCard(
                    image,
                    viewModel.getPokerTournamentById(currentId).description,
                    currentId
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    NameTextField(
                        nameInput,
                        0.8f,
                        onValueChange = { nameInput = it },
                        currentId
                    )
                }

                TournamentInfoText(viewModel, currentId)

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
                    onPreviousClick = {
                        val pokerTournaments = viewModel.uiState.value
                        currentId = pokerTournaments.first { it.pokerTournament.id < currentId }.pokerTournament.id

                        nameInput = viewModel.getPokerTournamentById(currentId).name
                    },
                    onNextClick = {
                        val pokerTournaments = viewModel.uiState.value
                        currentId = pokerTournaments.first { it.pokerTournament.id > currentId }.pokerTournament.id

                        nameInput = viewModel.getPokerTournamentById(currentId).name
                    },
                    onUpdateClick = {
                        val updatedTournament =
                            viewModel.getPokerTournamentById(currentId)
                        updatedTournament.name = nameInput

                        viewModel.updatePokerTournament(updatedTournament)
                    },
                    onCreateClick = {
                        showDialog = true
                    },
                    onDeleteClick = {
                        viewModel.deletePokerTournament(
                            viewModel.getPokerTournamentById(
                                currentId
                            )
                        )
                        currentId =
                            if (currentId > 1)
                                currentId - 1
                            else
                                viewModel.uiState.value.size
                        nameInput = viewModel.getPokerTournamentById(currentId).name
                    }
                )
            }
        }
    }
}

@Composable
fun NameTextField(nameInput: String, size: Float, onValueChange: (String) -> Unit, currentId: Int) {
    val isExceeding = remember { mutableStateOf(false) }
    val maxLength = 25

    LaunchedEffect(currentId)
    {
        isExceeding.value = nameInput.length > maxLength
    }
    TextField(
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        value = nameInput,
        onValueChange = {
            if (it.length <= maxLength) {
                onValueChange(it)
                isExceeding.value = false
            } else {
                isExceeding.value = true
            }
        },
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth(size)
            .background(Color.White),
        textStyle = TextStyle(fontSize = 18.sp),
        maxLines = 1,
        trailingIcon = {
            if (isExceeding.value) {
                Icon(
                    Icons.Default.Warning,
                    contentDescription = stringResource(R.string.exceeding_max_length),
                    tint = Color.Red
                )
            }
        },
        placeholder = {
            Text(text = stringResource(R.string.enter_name_max_characters, maxLength))
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun TournamentInfoText(pokerViewModel: PokerViewModel, currentId: Int) {
    Text(
        text = stringResource(id = R.string.max_players) + ": " + pokerViewModel.getPokerTournamentById(
            currentId
        ).playersMax,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(vertical = 4.dp),
        color = MaterialTheme.colorScheme.onSurface
    )
    Text(
        text = stringResource(id = R.string.current_players) + ": " + ": " + pokerViewModel.getPlayersByTournamentId(
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
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
fun NavigationButtons(
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    onUpdateClick: () -> Unit,
    onCreateClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = onPreviousClick,
            colors = ButtonDefaults.buttonColors()
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(id = R.string.previous)
            )
        }
        Button(
            onClick = onUpdateClick,
            colors = ButtonDefaults.buttonColors()
        ) {
            Icon(Icons.Filled.Edit, contentDescription = stringResource(id = R.string.update))
        }
        Button(
            onClick = onNextClick,
            colors = ButtonDefaults.buttonColors()
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = stringResource(id = R.string.next)
            )
        }
        Button(
            onClick = onCreateClick,
            colors = ButtonDefaults.buttonColors()
        ) {
            Icon(Icons.Filled.Add, contentDescription = stringResource(id = R.string.create))
        }
        Button(
            onClick = onDeleteClick,
            colors = ButtonDefaults.buttonColors()
        ) {
            Icon(Icons.Filled.Delete, contentDescription = stringResource(R.string.delete))
        }
    }
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
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        modifier = modifier
    )
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


@Composable
fun AddTournamentDialog(
    onAdd: (PokerTournament) -> Unit,
    onDismiss: () -> Unit
) {
    var name by rememberSaveable { mutableStateOf("") }
    var playersMax by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var ante by remember { mutableStateOf(false) }
    var levels by rememberSaveable { mutableStateOf("") }
    var startingStack by rememberSaveable { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(R.string.add_new_tournament)) },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(stringResource(R.string.name)) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    )
                )
                OutlinedTextField(
                    value = playersMax,
                    onValueChange = { playersMax = it },
                    label = { Text(stringResource(R.string.max_players)) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    )
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text(stringResource(R.string.description)) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    )
                )
                Row {
                    Text(stringResource(id =R.string.ante))
                    Switch(
                        checked = ante,
                        onCheckedChange = { ante = it }
                    )
                }
                OutlinedTextField(
                    value = levels,
                    onValueChange = { levels = it },
                    label = { Text(stringResource(R.string.levels)) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    )
                )
                OutlinedTextField(
                    value = startingStack,
                    onValueChange = { startingStack = it },
                    label = { Text(stringResource(R.string.starting_stack)) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                if (name.isNotEmpty() && playersMax.isNotEmpty() && description.isNotEmpty() && levels.isNotEmpty() && startingStack.isNotEmpty()) {
                    onAdd(
                        PokerTournament(
                            id = 0,
                            name = name,
                            playersMax = playersMax.toInt(),
                            description = description,
                            ante = ante,
                            levels = levels,
                            startingStack = startingStack.toInt(),
                            imageResourceId = R.drawable.ic_launcher_background
                        )
                    )
                    onDismiss()
                }
            }) {
                Text(stringResource(R.string.add))
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}


@Preview(locale = "nl")
@Composable
fun PokerPagePreview() {
    PokerPage()
}