package com.kilomobi.cosmo.domain

import com.kilomobi.cosmo.presentation.details.Device

class GetFilteredDevices {
    operator fun invoke(devices: List<Device>, lightFilter: Float): List<Device> {
        return devices.filter { it.lightValue != null && it.lightValue <= lightFilter }
    }
}