package com.kilomobi.cosmo.domain

import com.kilomobi.cosmo.data.DevicesRepository
import com.kilomobi.cosmo.presentation.details.Device
import javax.inject.Inject

class GetDevicesUseCase @Inject constructor(
    private val repository: DevicesRepository
) {
    suspend operator fun invoke(): List<Device> {
        repository.loadDevices()
        return repository.devices
    }
}