package com.kilomobi.cosmo.data

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.util.Log
import com.kilomobi.cosmo.domain.BluetoothRepository
import com.kilomobi.cosmo.domain.usecase.StartBluetoothScanUseCase
import com.kilomobi.cosmo.presentation.bluetooth.CosmoListItemDevice
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject

@SuppressLint("MissingPermission")
class CosmoBluetoothScanner @Inject constructor(
    private val bluetoothAdapter: BluetoothAdapter?
) : BluetoothRepository {
    private var _devices = mutableListOf<CosmoListItemDevice>()
    private var currentScanCallback: ScanCallback? = null

    val devices: List<CosmoListItemDevice>
        get() = _devices

    override suspend fun startScan(): Flow<CosmoListItemDevice> = callbackFlow {
        try {
            if (bluetoothAdapter?.isEnabled == false) {
                close(BluetoothNotEnabledException("Bluetooth is not enabled"))
                return@callbackFlow
            }

            currentScanCallback = object : ScanCallback() {
                override fun onScanResult(callbackType: Int, result: ScanResult) {
                    val device = CosmoListItemDevice(result.device.name, result.device.address)
                    if (!devices.contains(device)) {
                        _devices.add(device)
                        trySend(device)
                    }
                }

                override fun onScanFailed(errorCode: Int) {
                    close(BluetoothScanFailedException("Bluetooth scan failed with error code: $errorCode"))
                }
            }

            val scanner = bluetoothAdapter?.bluetoothLeScanner
            scanner?.startScan(currentScanCallback)

            awaitClose {
                scanner?.stopScan(currentScanCallback)
            }
        } catch (e: Exception) {
            close(e)
        }
    }.onCompletion { cause ->
        if (cause != null) {
            stopScan()
        }
    }

    override suspend fun stopScan() {
        currentScanCallback?.let { callback ->
            bluetoothAdapter?.bluetoothLeScanner?.stopScan(callback)
        }

        currentScanCallback = null
    }
}

class BluetoothNotEnabledException(message: String) : Exception(message)
class BluetoothScanFailedException(message: String) : Exception(message)