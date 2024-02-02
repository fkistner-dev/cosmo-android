package com.kilomobi.cosmo.presentation.details

import android.bluetooth.BluetoothDevice
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kilomobi.cosmo.domain.StartBluetoothScanUseCase
import com.kilomobi.cosmo.domain.StopBluetoothScanUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class BluetoothViewModel @Inject constructor(
    private val startBluetoothScanUseCase: StartBluetoothScanUseCase,
    private val stopBluetoothScanUseCase: StopBluetoothScanUseCase
) : ViewModel() {

    private val bluetoothScanResults = MutableLiveData<List<BluetoothDevice>>()

    fun getBluetoothScanResults(): LiveData<List<BluetoothDevice>> = bluetoothScanResults

    fun startBluetoothScan() {
        viewModelScope.launch {
            try {
                val devices = startBluetoothScanUseCase.execute()
                bluetoothScanResults.value = devices
            } catch (e: Exception) {
                // Gérer les erreurs
            }
        }
    }

    fun stopBluetoothScan() {
        viewModelScope.launch {
            try {
                stopBluetoothScanUseCase.execute()
            } catch (e: Exception) {
                Log.d(BluetoothViewModel::class.java.name, "Error caught while stopping scan ${e.message}")
            }
        }
    }
}
