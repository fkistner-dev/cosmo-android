package com.kilomobi.cosmo.data

import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.util.Log
import com.kilomobi.cosmo.domain.BluetoothRepository
import com.kilomobi.cosmo.presentation.bluetooth.CosmoListItemDevice
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject

class CosmoBluetoothScanner @Inject constructor(
    private val bluetoothAdapter: BluetoothAdapter?
) : BluetoothRepository {
    override suspend fun startScan(): List<CosmoListItemDevice> = coroutineScope {
        return@coroutineScope try {
            suspendCancellableCoroutine { continuation ->
                if (bluetoothAdapter?.isEnabled == false) {
                    continuation.cancel(BluetoothNotEnabledException("Bluetooth is not enabled"))
                    return@suspendCancellableCoroutine
                }

                val devices = mutableListOf<CosmoListItemDevice>()

                val scanCallback = object : ScanCallback() {
                    override fun onScanResult(callbackType: Int, result: ScanResult) {
                        val device = CosmoListItemDevice(result.device.name, result.device.address)
                        if (!devices.contains(device))
                            devices.add(device)
                    }

                    override fun onScanFailed(errorCode: Int) {
                        continuation.cancel(BluetoothScanFailedException("Bluetooth scan failed with error code: $errorCode"))
                    }
                }

                val scanner = bluetoothAdapter?.bluetoothLeScanner
                scanner?.startScan(scanCallback)

                continuation.invokeOnCancellation {
                    scanner?.stopScan(scanCallback)
                }

                launch {
                    delay(10000)
                    scanner?.stopScan(scanCallback)
                    continuation.resume(devices) {
                        it.printStackTrace()
                    }
                }
            }
        } catch (e: Exception) {
            Log.d(CosmoBluetoothScanner::class.java.name, "Error caught while scanning : ${e.message}")
            emptyList()
        }
    }

    override suspend fun stopScan() {
        // TODO
    }
}

class BluetoothNotEnabledException(message: String) : Exception(message)
class BluetoothScanFailedException(message: String) : Exception(message)