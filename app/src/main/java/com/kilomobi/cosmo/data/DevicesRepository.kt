package com.kilomobi.cosmo.data

import com.kilomobi.cosmo.data.di.IoDispatcher
import com.kilomobi.cosmo.data.local.DeviceDao
import com.kilomobi.cosmo.data.local.LocalDevice
import com.kilomobi.cosmo.data.remote.CosmoApiService
import com.kilomobi.cosmo.domain.model.CosmoDevice
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject

class DevicesRepository @Inject constructor(
    private val restInterface: CosmoApiService,
    private val deviceDao: DeviceDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    private var _devices = listOf<CosmoDevice>()

    val devices: List<CosmoDevice>
        get() = _devices

    suspend fun getDevices(): List<CosmoDevice> {
        return withContext(Dispatchers.IO) {
            return@withContext deviceDao.getAll().map {
                CosmoDevice(
                    macAddress = it.macAddress,
                    model = it.model,
                    product = it.product,
                    firmwareVersion = it.firmwareVersion,
                    installationMode = it.installationMode,
                    brakeLight = it.brakeLight,
                    lightMode = it.lightMode,
                    lightAuto = it.lightAuto,
                    lightValue = it.lightValue
                )
            }
        }
    }

    suspend fun loadDevices() {
        return withContext(Dispatchers.IO) {
            try {
                refreshCache()
            } catch (e: Exception) {
                when (e) {
                    is UnknownHostException,
                    is ConnectException,
                    is HttpException -> {
                        if (deviceDao.getAll().isEmpty())
                            throw Exception("Something went wrong. We have no data.")
                    }

                    else -> throw e
                }
            }
        }
    }

    private suspend fun refreshCache() {
        val remoteDevices = restInterface.getDevices()
        deviceDao.addAll(remoteDevices.devices.map {
            LocalDevice(
                macAddress = it.macAddress,
                model = it.model,
                product = it.product,
                firmwareVersion = it.firmwareVersion,
                serial = it.serial,
                installationMode = it.installationMode,
                brakeLight = it.brakeLight,
                lightMode = it.lightMode,
                lightAuto = it.lightAuto,
                lightValue = it.lightValue
            )
        })
    }
}