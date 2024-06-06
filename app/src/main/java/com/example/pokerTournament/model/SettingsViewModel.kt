package com.example.pokerTournament.model

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokerTournament.R
import com.example.pokerTournament.data.SettingsRepository
import com.example.pokerTournament.ui.theme.DarkColorScheme
import com.example.pokerTournament.ui.theme.LightColorScheme
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val preferencesRepository: SettingsRepository,
                   @ApplicationContext private val context: Context) : ViewModel() {

    var theme by mutableStateOf(preferencesRepository.getTheme())
    var language by mutableStateOf(preferencesRepository.getLanguage())
    var colorScheme by mutableStateOf(if (theme == context.getString(R.string.light)) LightColorScheme else DarkColorScheme)

    init {
        setLocale(if (language == context.getString(R.string.english)) "en" else "nl")
    }

    fun onThemeChange(newTheme: String) {
        theme = newTheme
        colorScheme = if (theme == context.getString(R.string.light)) LightColorScheme else DarkColorScheme
        viewModelScope.launch {
            preferencesRepository.saveTheme(newTheme)
            applyTheme(newTheme)
        }
    }

    fun onLanguageChange(newLanguage: String) {
        language = newLanguage
        viewModelScope.launch {
            preferencesRepository.saveLanguage(newLanguage)
            setLocale(if (newLanguage == context.getString(R.string.english)) "en" else "nl")
        }
    }

    private fun setLocale(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        context.createConfigurationContext(config)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }


    private fun applyTheme(theme: String) {
        Log.d("SettingsViewModel", "setTheme: $theme")
        colorScheme = if (theme == context.getString(R.string.light)) LightColorScheme else DarkColorScheme
    }

    companion object {

        private var instance: SettingsViewModel? = null

        fun getInstance(preferencesRepository: SettingsRepository,context: Context): SettingsViewModel {
            if (instance == null) {
                instance = SettingsViewModel(preferencesRepository, context)
            }
            return instance!!
        }
    }
}