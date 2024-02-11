package com.kilomobi.cosmo

import com.kilomobi.cosmo.domain.BluetoothRepository
import com.kilomobi.cosmo.presentation.MockContent
import com.kilomobi.cosmo.presentation.bluetooth.CosmoListItemDevice
import kotlinx.coroutines.delay

class FakeBluetoothScanner : BluetoothRepository {
    override suspend fun startScan(): List<CosmoListItemDevice> {
        delay(1000)
        return MockContent.getBluetoothList()
    }

    override suspend fun stopScan() {
        TODO("Not yet implemented")
    }
}