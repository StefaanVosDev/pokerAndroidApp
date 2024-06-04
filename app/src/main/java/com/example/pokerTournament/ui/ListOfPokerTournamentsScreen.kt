package com.example.pokerTournament.ui

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokerTournament.R
import com.example.pokerTournament.data.PokerTournamentUiState
import com.example.pokerTournament.model.PokerViewModel

@Composable
fun ListOfPokerTournamentsScreen(
    viewModel: PokerViewModel,
    uiState: List<PokerTournamentUiState>,
    onNavigateDetailsScreen : (Int) ->   Unit,
){
    var showDialog by rememberSaveable { mutableStateOf(false) }

    LazyColumn {
        items(uiState, key = { it.pokerTournament.id }) { state ->
            Log.d("Compose", "Rendering PokerTournamentItem: ${state.pokerTournament}")

            PokerTournamentItem(
                uiState = state,
                onExpandToggle = { viewModel.toggleExpand(state.pokerTournament.id) },
                onNavigateDetailsScreen = { onNavigateDetailsScreen (state.pokerTournament.id) },
                onDelete = { viewModel.deletePokerTournament(state.pokerTournament) },
                viewModel = viewModel
            )
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp), contentAlignment = Alignment.BottomEnd) {
        FloatingActionButton(onClick = { showDialog = true }) {
            Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add_pokertournament))
        }
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
}


@Composable
fun PokerTournamentItem(
    uiState: PokerTournamentUiState,
    onExpandToggle: () -> Unit,
    onNavigateDetailsScreen: (Int) -> Unit,
    onDelete: () -> Unit,
    viewModel: PokerViewModel
) {
    val pokerTournament = uiState.pokerTournament

    Card(
        modifier = Modifier
            .padding(8.dp)
    )
    {
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioHighBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
                .background(color = Color.Gray)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.padding(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = painterResource(pokerTournament.imageResourceId),
                        contentDescription = null,
                        modifier = Modifier
                            .size(90.dp)
                            .clip(MaterialTheme.shapes.small),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(16.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        PokerTournamentDetails(
                            name = pokerTournament.name,
                            maxPlayers = pokerTournament.playersMax,
                            currentPlayers = viewModel.getPlayersByTournamentId(pokerTournament.id).size
                        )
                    }
                    AppItemButton(expanded = uiState.isExpanded, onClick = onExpandToggle)
                }
            }
            if (uiState.isExpanded) {
                Text(
                    text = pokerTournament.description,
                    style = TextStyle(fontSize = 16.sp, fontFamily = FontFamily.Serif)
                )
                Row {

                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier
                            .graphicsLayer(
                                alpha = animateFloatAsState(
                                    targetValue = 1f,
                                    label = ""
                                ).value
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete PokerTournament",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                    IconButton(
                        onClick = { onNavigateDetailsScreen(pokerTournament.id) },
                        modifier = Modifier
                            .graphicsLayer(
                                alpha = animateFloatAsState(
                                    targetValue = 1f,
                                    label = ""
                                ).value
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = stringResource(R.string.edit_pokertournament),
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AppItemButton(expanded: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(onClick = onClick, modifier = modifier) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = stringResource(R.string.expand_button),
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}


@Composable
fun PokerTournamentDetails(
    name: String,
    maxPlayers: Int,
    currentPlayers: Int
) {
    Column {
        Text(
            text = name,
            style = TextStyle(fontSize = 20.sp, fontFamily = FontFamily.Serif)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Players: $currentPlayers",
            style = TextStyle(fontSize = 16.sp, fontFamily = FontFamily.Serif)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Max Players: $maxPlayers",
            style = TextStyle(fontSize = 16.sp, fontFamily = FontFamily.Serif)
        )
    }
}