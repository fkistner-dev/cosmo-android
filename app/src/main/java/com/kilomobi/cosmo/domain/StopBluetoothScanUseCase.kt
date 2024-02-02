package com.kilomobi.cosmo.domain

import com.kilomobi.cosmo.BluetoothRepository
import javax.inject.Inject

class StopBluetoothScanUseCase @Inject constructor(
    private val bluetoothRepository: BluetoothRepository
) {
    suspend fun execute() {
        bluetoothRepository.stopBluetoothScan()
    }
}