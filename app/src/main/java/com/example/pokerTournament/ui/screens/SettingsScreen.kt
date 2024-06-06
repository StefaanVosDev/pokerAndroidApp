package com.example.pokerTournament.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.pokerTournament.R
import com.example.pokerTournament.model.SettingsViewModel


@Composable
fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel()) {
    Column(modifier = Modifier.padding(16.dp)) {

        Text(
            text = stringResource(R.string.theme_change_disclaimer),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(16.dp))

        ThemeSettingItem(theme = viewModel.theme, onThemeChange = viewModel::onThemeChange)
        Spacer(modifier = Modifier.height(16.dp))
        LanguageSettingItem(language = viewModel.language, onLanguageChange = viewModel::onLanguageChange)
    }
}

@Composable
fun ThemeSettingItem(theme: String, onThemeChange: (String) -> Unit) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    val themes = listOf(stringResource(R.string.light), stringResource(R.string.dark))

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
    ) {
        Text(
            text = stringResource(R.string.theme),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Icon(Icons.Filled.ArrowDropDown, contentDescription = null)

        Spacer(modifier = Modifier.weight(1f))
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(150.dp),
        ) {
            themes.forEach { dropdownTheme ->
                DropdownMenuItem(
                    text = {
                           Text(
                               text = dropdownTheme,
                               color = if (dropdownTheme == theme) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                           )
                    },
                    onClick = {
                        onThemeChange(dropdownTheme)
                        expanded = false
                    },
                )
            }
        }
    }
}

@Composable
fun LanguageSettingItem(language: String, onLanguageChange: (String) -> Unit) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    val languages = listOf(stringResource(R.string.english), stringResource(R.string.dutch))

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
    ) {
        Text(
            text = stringResource(R.string.language),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
        Spacer(modifier = Modifier.weight(1f))
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(150.dp)
                .padding(end = 16.dp)
        ) {
            languages.forEach { dropdownLanguage ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = dropdownLanguage,
                            color = if (dropdownLanguage == language) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    },
                    onClick = {
                        onLanguageChange(dropdownLanguage)
                        expanded = false
                    },
                )
            }
        }
    }
}