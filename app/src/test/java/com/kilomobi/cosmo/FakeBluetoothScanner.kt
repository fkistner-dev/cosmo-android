package com.kilomobi.cosmo

import android.bluetooth.BluetoothDevice
import com.kilomobi.cosmo.domain.BluetoothScanner
import kotlinx.coroutines.delay

class FakeBluetoothScanner : BluetoothScanner {
    override suspend fun startScan(): List<BluetoothDevice> {
        delay(1000)
        return DummyContent.getBluetoothList()
    }

    override suspend fun stopScan() {
        TODO("Not yet implemented")
    }
}