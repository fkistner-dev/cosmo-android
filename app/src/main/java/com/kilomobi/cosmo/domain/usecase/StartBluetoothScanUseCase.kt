package com.kilomobi.cosmo.domain.usecase

import com.kilomobi.cosmo.domain.BluetoothRepository
import com.kilomobi.cosmo.presentation.bluetooth.CosmoListItemDevice
import javax.inject.Inject

class StartBluetoothScanUseCase @Inject constructor(
    private val bluetoothRepository: BluetoothRepository
) {
    suspend operator fun invoke(): List<CosmoListItemDevice> {
        return bluetoothRepository.startScan()
    }
}
