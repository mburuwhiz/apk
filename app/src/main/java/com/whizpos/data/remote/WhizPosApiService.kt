package com.whizpos.data.remote

import com.whizpos.data.remote.dto.SyncDataDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface WhizPosApiService {

    @GET("/api/sync")
    suspend fun getSyncData(
        @Header("Authorization") apiKey: String
    ): SyncDataDto

    @POST("/api/sync")
    suspend fun pushData(
        @Header("Authorization") apiKey: String,
        @Body operations: List<SyncOperation>
    )
}
