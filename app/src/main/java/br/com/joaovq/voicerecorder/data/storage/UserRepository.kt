package br.com.joaovq.voicerecorder.data.storage

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepository(private val context: Context) {
    private val userPreferences = context.datastore
    private val darkPreferenceKey = booleanPreferencesKey("dark-theme")
    fun getDarkThemePreference(): Flow<Boolean> {
        return userPreferences.data.map { it[darkPreferenceKey] ?: false }
    }

    suspend fun setDarkThemePreference(value: Boolean) {
        userPreferences.edit {
            it[darkPreferenceKey] = value
        }
    }
}