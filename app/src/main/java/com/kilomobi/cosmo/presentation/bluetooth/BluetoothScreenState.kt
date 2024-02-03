package com.kilomobi.cosmo.presentation.bluetooth

import android.bluetooth.BluetoothDevice

data class BluetoothScreenState(
    val permissionGranted: Boolean,
    val bluetoothDevices: List<BluetoothDevice>,
    val isSearching: Boolean,
    val error: String? = null
)