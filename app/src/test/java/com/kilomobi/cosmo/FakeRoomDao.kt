package com.kilomobi.cosmo

import com.kilomobi.cosmo.data.local.DeviceDao
import com.kilomobi.cosmo.data.local.LocalDevice
import kotlinx.coroutines.delay

class FakeRoomDao : DeviceDao {
    private var devices = HashMap<String, LocalDevice>()
    override suspend fun getAll()
            : List<LocalDevice> {
        delay(1000)
        return devices.values.toList()
    }

    override suspend fun addAll(localDevices: List<LocalDevice>) {
        localDevices.forEach {
            this.devices[it.macAddress] = it
        }
    }
}
