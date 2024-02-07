package com.kilomobi.cosmo

import com.kilomobi.cosmo.domain.BluetoothRepository
import com.kilomobi.cosmo.presentation.DummyContent
import com.kilomobi.cosmo.presentation.bluetooth.CosmoListItemDevice
import kotlinx.coroutines.delay

class FakeBluetoothScanner : BluetoothRepository {
    override suspend fun startScan(): List<CosmoListItemDevice> {
        delay(1000)
        return DummyContent.getBluetoothList()
    }

    override suspend fun stopScan() {
        TODO("Not yet implemented")
    }
}