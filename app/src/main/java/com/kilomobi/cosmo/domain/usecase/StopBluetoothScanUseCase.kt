package com.kilomobi.cosmo.domain.usecase

import android.util.Log
import com.kilomobi.cosmo.domain.BluetoothRepository
import javax.inject.Inject

class StopBluetoothScanUseCase @Inject constructor(
    private val bluetoothRepository: BluetoothRepository
) {
    suspend operator fun invoke() {
        bluetoothRepository.stopScan()
    }
}