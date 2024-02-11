package com.kilomobi.cosmo.domain.usecase

import android.util.Log
import com.kilomobi.cosmo.domain.BluetoothRepository
import com.kilomobi.cosmo.presentation.bluetooth.CosmoListItemDevice
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StartBluetoothScanUseCase @Inject constructor(
    private val bluetoothRepository: BluetoothRepository
) {
    suspend operator fun invoke(): Flow<CosmoListItemDevice> {
        return bluetoothRepository.startScan()
    }
}
