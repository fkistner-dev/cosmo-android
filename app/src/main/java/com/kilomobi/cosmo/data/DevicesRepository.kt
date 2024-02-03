package com.kilomobi.cosmo.data

import com.kilomobi.cosmo.data.di.IoDispatcher
import com.kilomobi.cosmo.data.remote.CosmoApiService
import com.kilomobi.cosmo.presentation.details.Device
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject

class DevicesRepository @Inject constructor(
    private val restInterface: CosmoApiService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    private var _devices = listOf<Device>()

    val devices: List<Device>
        get() = _devices

    suspend fun loadDevices() {
        return withContext(Dispatchers.IO) {
            try {
                val remoteDevices = restInterface.getDevices()
                _devices = remoteDevices.devices
            } catch (e: Exception) {
                when (e) {
                    is UnknownHostException,
                    is ConnectException,
                    is HttpException -> {
                        throw Exception("Something went wrong. We have no data.")
                    }
                    else -> throw e
                }
            }
        }
    }
}