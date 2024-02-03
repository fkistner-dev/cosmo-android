package com.kilomobi.cosmo.data.remote

import com.kilomobi.cosmo.presentation.list.RemoteDevices
import retrofit2.http.GET

interface CosmoApiService {
    @GET("test/devices")
    suspend fun getDevices(): RemoteDevices
}