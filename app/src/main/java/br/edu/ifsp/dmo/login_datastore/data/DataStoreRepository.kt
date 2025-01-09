package br.edu.ifsp.dmo.login_datastore.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import br.edu.ifsp.dmo.login_datastore.util.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreRepository(context: Context) {
    private val dataStore: DataStore<Preferences> = context.dataStore

    object PreferencesFile {
        const val FILE_NAME = "user_preferences"
    }

    private object PreferencesKey {
        val SAVE_LOGIN = booleanPreferencesKey("save_login")
        val STAY_LOGGED_IN = booleanPreferencesKey("stay_logged_in")
        val EMAIL = stringPreferencesKey("email")
        val PASSWORD = longPreferencesKey("password")
    }

    suspend fun savePreferences(email: String = "", password: Long = 0L, saveLogin: Boolean, stayLoggedIn: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.EMAIL] = email
            preferences[PreferencesKey.PASSWORD] = password
            preferences[PreferencesKey.SAVE_LOGIN] = saveLogin
            preferences[PreferencesKey.STAY_LOGGED_IN] = stayLoggedIn
        }
    }

    val loginPreferences: Flow<Pair<Boolean, Boolean>> = dataStore.data.map { preferences ->
        val saveLogin = preferences[PreferencesKey.SAVE_LOGIN] ?: false
        val stayLoggedIn = preferences[PreferencesKey.STAY_LOGGED_IN] ?: false
        Pair(saveLogin, stayLoggedIn)
    }

    val dataPreferences: Flow<Pair<String, Long>> = dataStore.data.map { preferences ->
        val email = preferences[PreferencesKey.EMAIL] ?: ""
        val password = preferences[PreferencesKey.PASSWORD] ?: 0L
        Pair(email, password)
    }
}