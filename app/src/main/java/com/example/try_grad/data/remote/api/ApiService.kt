package com.example.try_grad.data.remote.api

import com.example.try_grad.data.remote.models.DeviceReportRequest
import com.example.try_grad.data.remote.models.SyncResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    /**
     * Upload the full device scan report to the central server.
     * The server stores all app data associated with this device ID.
     */
    @POST("api/v1/reports")
    suspend fun uploadReport(
        @Body request: DeviceReportRequest
    ): Response<SyncResponse>

    /**
     * Check the server-side risk summary for this device.
     */
    @GET("api/v1/reports/{deviceId}/summary")
    suspend fun getDeviceSummary(
        @Path("deviceId") deviceId: String
    ): Response<SyncResponse>
}
