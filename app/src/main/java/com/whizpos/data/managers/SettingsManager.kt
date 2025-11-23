package com.whizpos.data.managers

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

data class ConnectionDetails(val serverUrl: String?, val apiKey: String?)

@Singleton
class SettingsManager @Inject constructor(@ApplicationContext private val context: Context) {

    private val SERVER_URL = stringPreferencesKey("server_url")
    private val API_KEY = stringPreferencesKey("api_key")

    val connectionDetails: Flow<ConnectionDetails> = context.dataStore.data.map {
        ConnectionDetails(
            serverUrl = it[SERVER_URL],
            apiKey = it[API_KEY]
        )
    }

    suspend fun saveConnectionDetails(url: String, apiKey: String) {
        context.dataStore.edit {
            it[SERVER_URL] = url
            it[API_KEY] = apiKey
        }
    }

    suspend fun clearConnectionDetails() {
        context.dataStore.edit {
            it.remove(SERVER_URL)
            it.remove(API_KEY)
        }
    }
}