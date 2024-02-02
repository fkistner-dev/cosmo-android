package com.kilomobi.cosmo.domain

import android.bluetooth.BluetoothDevice
import com.kilomobi.cosmo.BluetoothRepository
import javax.inject.Inject

class StartBluetoothScanUseCase @Inject constructor(
    private val bluetoothRepository: BluetoothRepository
) {
    suspend fun execute(): List<BluetoothDevice> {
        return bluetoothRepository.startBluetoothScan()
    }
}
