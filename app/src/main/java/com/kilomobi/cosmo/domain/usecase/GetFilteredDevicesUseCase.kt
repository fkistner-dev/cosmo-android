package com.kilomobi.cosmo.domain.usecase

import com.kilomobi.cosmo.domain.model.CosmoDevice

class GetFilteredDevicesUseCase {
    operator fun invoke(devices: List<CosmoDevice>, lightFilter: Float): List<CosmoDevice> {
        return devices.filter { it.lightValue != null && it.lightValue <= lightFilter }
    }
}