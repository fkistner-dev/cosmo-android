package com.kilomobi.cosmo.domain

import com.kilomobi.cosmo.presentation.bluetooth.CosmoListItemDevice

interface BluetoothRepository {
    suspend fun startScan(): List<CosmoListItemDevice>
    suspend fun stopScan()
}