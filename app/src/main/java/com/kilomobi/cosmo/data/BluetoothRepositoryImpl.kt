package com.kilomobi.cosmo.data

import com.kilomobi.cosmo.domain.BluetoothRepository
import com.kilomobi.cosmo.presentation.bluetooth.CosmoListItemDevice
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BluetoothRepositoryImpl @Inject constructor(
    private val bluetoothScanner: BluetoothRepository
) : BluetoothRepository {

    override suspend fun startScan(): Flow<CosmoListItemDevice> {
        return withContext(Dispatchers.IO) {
            bluetoothScanner.startScan()
        }
    }

    override suspend fun stopScan() {
        return withContext(Dispatchers.IO) {
            bluetoothScanner.stopScan()
        }
    }
}