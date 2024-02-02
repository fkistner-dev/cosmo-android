package com.kilomobi.cosmo

import retrofit2.http.GET

interface CosmoApiService {
    @GET("test/devices")
    suspend fun getDevices(): RemoteDevices
}