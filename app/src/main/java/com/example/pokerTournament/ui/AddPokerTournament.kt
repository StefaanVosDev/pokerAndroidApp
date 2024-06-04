package com.example.pokerTournament.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.example.pokerTournament.R
import com.example.pokerTournament.model.PokerTournament


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
                Row {
                    Text(stringResource(id = R.string.ante))
                    Switch(
                        checked = ante,
                        onCheckedChange = { ante = it }
                    )
                }
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
                            levels = levels,
                            startingStack = startingStack.toInt(),
                            ante = ante,
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