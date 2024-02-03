package com.kilomobi.cosmo.data

import android.bluetooth.BluetoothDevice
import com.kilomobi.cosmo.domain.BluetoothScanner
import javax.inject.Inject

interface BluetoothRepository {
    suspend fun startBluetoothScan(): List<BluetoothDevice>
    suspend fun stopBluetoothScan()
}

class BluetoothRepositoryImpl @Inject constructor(
    private val bluetoothScanner: BluetoothScanner
) : BluetoothRepository {

    override suspend fun startBluetoothScan(): List<BluetoothDevice> {
        return bluetoothScanner.startScan()
    }

    override suspend fun stopBluetoothScan() {
        bluetoothScanner.stopScan()
    }
}
