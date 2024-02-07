package com.kilomobi.cosmo.data

import com.kilomobi.cosmo.domain.BluetoothRepository
import com.kilomobi.cosmo.presentation.bluetooth.CosmoListItemDevice
import javax.inject.Inject

class BluetoothRepositoryImpl @Inject constructor(
    private val bluetoothScanner: BluetoothRepository
) : BluetoothRepository {

    override suspend fun startScan(): List<CosmoListItemDevice> {
        return bluetoothScanner.startScan()
    }

    override suspend fun stopScan() {
        bluetoothScanner.stopScan()
    }
}