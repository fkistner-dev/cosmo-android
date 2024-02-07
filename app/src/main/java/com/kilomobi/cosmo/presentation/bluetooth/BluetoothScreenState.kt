package com.kilomobi.cosmo.presentation.bluetooth

data class BluetoothScreenState(
    val permissionGranted: Boolean,
    val bluetoothDevices: List<CosmoListItemDevice>,
    val isSearching: Boolean,
    val error: String? = null
)