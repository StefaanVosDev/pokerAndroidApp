package com.example.pokerTournament

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokerTournament.model.PokerViewModel
import com.example.pokerTournament.ui.DetailsPokerTournamentScreen
import com.example.pokerTournament.ui.ListOfPokerTournamentsScreen


enum class PokerScreen(@StringRes val title: Int) {
    Start(title = R.string.pokahnights),
    DetailsPokerTournament(title = R.string.tournament)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokerTopAppBar(
    currentScreen: PokerScreen,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
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
                    text = stringResource(currentScreen.title),
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (navController.previousBackStackEntry != null) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        }
    )
}


@Composable
fun PokerPage(
    viewModel: PokerViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val uiState by viewModel.uiState.collectAsState()

    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute = backStackEntry?.destination?.route?.substringBefore("/{id}") ?: PokerScreen.Start.name
    val currentScreen = PokerScreen.valueOf(currentRoute)


    Scaffold(
        topBar = {
            PokerTopAppBar(
                currentScreen = currentScreen,
                navController = navController
            )
        },
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = PokerScreen.Start.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            composable(route = PokerScreen.Start.name) {
                ListOfPokerTournamentsScreen(
                    uiState = uiState,
                    viewModel = viewModel,
                    onNavigateDetailsScreen = {id ->
                        navController.navigate(
                            "${PokerScreen.DetailsPokerTournament.name}/$id") {
                            launchSingleTop = true
                            popUpTo(PokerScreen.Start.name) {
                                inclusive = false
                            }
                        }
                    },
                )
            }
            composable(
                route = "${PokerScreen.DetailsPokerTournament.name}/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id") ?: 1
                DetailsPokerTournamentScreen(
                    uiState = uiState,
                    id = id,
                    viewModel = viewModel,
                    onSave = { updatedPokerTournament ->
                        viewModel.savePokerTournament(updatedPokerTournament)
                             },
                    onDeleteClick = { deletedPokerTournament ->
                        viewModel.deletePokerTournament(deletedPokerTournament)
                        },
                    onEditClick = { editPokerTournamentId ->
                        viewModel.startEditMode(editPokerTournamentId)},
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }
    }
}