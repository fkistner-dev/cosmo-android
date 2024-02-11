package com.kilomobi.cosmo.domain

import com.kilomobi.cosmo.presentation.bluetooth.CosmoListItemDevice
import kotlinx.coroutines.flow.Flow

interface BluetoothRepository {
    suspend fun startScan(): Flow<CosmoListItemDevice>
    suspend fun stopScan()
}