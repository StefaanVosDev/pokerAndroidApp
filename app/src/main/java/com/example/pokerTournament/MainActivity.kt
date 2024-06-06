package com.example.pokerTournament

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.pokerTournament.ui.theme.ArtSpaceProjectTheme
import androidx.compose.ui.tooling.preview.Preview
import com.example.pokerTournament.data.SettingsRepository
import com.example.pokerTournament.model.SettingsViewModel
import com.example.pokerTournament.ui.PokerPage
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var preferencesRepository: SettingsRepository
    private lateinit var defaultSettingsModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate: called")
        super.onCreate(savedInstanceState)

        preferencesRepository = SettingsRepository(this)
        defaultSettingsModel = SettingsViewModel(preferencesRepository, this)

        setContent {
            ArtSpaceProjectTheme(
            ) {
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



@Preview(locale = "nl")
@Composable
fun PokerPagePreview() {
    PokerPage()
}