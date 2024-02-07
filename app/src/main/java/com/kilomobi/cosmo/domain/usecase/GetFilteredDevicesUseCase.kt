package com.kilomobi.cosmo.domain.usecase

import com.kilomobi.cosmo.data.remote.RemoteDevice

class GetFilteredDevicesUseCase {
    operator fun invoke(devices: List<RemoteDevice>, lightFilter: Float): List<RemoteDevice> {
        return devices.filter { it.lightValue != null && it.lightValue <= lightFilter }
    }
}