package com.kilomobi.cosmo.domain

import android.bluetooth.BluetoothDevice

interface BluetoothScanner {
    suspend fun startScan(): List<BluetoothDevice>
    suspend fun stopScan()
}