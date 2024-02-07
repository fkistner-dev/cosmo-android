package com.kilomobi.cosmo.domain.usecase

import com.kilomobi.cosmo.domain.BluetoothRepository
import javax.inject.Inject

class StopBluetoothScanUseCase @Inject constructor(
    private val bluetoothRepository: BluetoothRepository
) {
    suspend fun invoke() {
        bluetoothRepository.stopScan()
    }
}