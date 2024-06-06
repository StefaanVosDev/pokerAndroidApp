package com.example.pokerTournament.data

import android.content.Context
import android.util.Log

class SettingsRepository(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    fun getTheme(): String {
        return sharedPreferences.getString("theme", "Light") ?: "Light"
    }

    fun getLanguage(): String {
        return sharedPreferences.getString("language", "English") ?: "English"
    }

    fun saveTheme(theme: String) {
        Log.d("PreferencesRepository", "saveTheme: $theme")
        sharedPreferences.edit().putString("theme", theme).apply()
    }

    fun saveLanguage(language: String) {
        sharedPreferences.edit().putString("language", language).apply()
    }
}