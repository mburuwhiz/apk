package com.whizpos.di

import com.whizpos.data.managers.SettingsManager
import com.whizpos.data.remote.WhizPosApiService
import kotlinx.coroutines.flow.first
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Provider

class ApiProvider @Inject constructor(
    private val retrofitBuilder: Retrofit.Builder,
    private val settingsManager: SettingsManager
) : Provider<WhizPosApiService> {

    override fun get(): WhizPosApiService {
        // This is not ideal for performance, as it blocks to get the URL.
        // A better approach would involve a service factory that is aware of the connection state.
        val url = kotlinx.coroutines.runBlocking { settingsManager.connectionDetails.first().serverUrl }
        return retrofitBuilder
            .baseUrl(url ?: "http://localhost/")
            .build()
            .create(WhizPosApiService::class.java)
    }
}