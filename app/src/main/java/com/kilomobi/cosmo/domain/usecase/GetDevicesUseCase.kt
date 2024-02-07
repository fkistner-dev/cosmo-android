package com.kilomobi.cosmo.domain.usecase

import com.kilomobi.cosmo.data.DevicesRepository
import com.kilomobi.cosmo.data.remote.RemoteDevice
import javax.inject.Inject

class GetDevicesUseCase @Inject constructor(
    private val repository: DevicesRepository
) {
    suspend operator fun invoke(): List<RemoteDevice> {
        repository.loadDevices()
        return repository.devices
    }
}