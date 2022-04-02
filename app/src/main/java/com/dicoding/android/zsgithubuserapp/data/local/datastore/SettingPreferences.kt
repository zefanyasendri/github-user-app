package com.dicoding.android.zsgithubuserapp.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("settings")

class SettingPreferences constructor(context: Context) {
    private val key = booleanPreferencesKey("theme_setting")
    private val dataStore = context.dataStore

    fun getThemeSetting(): Flow<Boolean> =
        dataStore.data.map { preferences ->
            preferences[key] ?: false
        }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[key] = isDarkModeActive
        }
    }
}